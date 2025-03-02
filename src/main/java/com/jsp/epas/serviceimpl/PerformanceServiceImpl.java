package com.jsp.epas.serviceimpl;

import com.jsp.epas.entity.Appraisal;
import com.jsp.epas.entity.Category;
import com.jsp.epas.entity.Employee;
import com.jsp.epas.enums.Rating;
import com.jsp.epas.repository.AppraisalRepository;
import com.jsp.epas.repository.CategoryRepository;
import com.jsp.epas.repository.EmployeeRepository;
import com.jsp.epas.service.PerformanceService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class PerformanceServiceImpl implements PerformanceService {

    private final EmployeeRepository employeeRepository;
    private final CategoryRepository categoryRepository;
    private final AppraisalRepository appraisalRepository;

    // Threshold for significant deviation that requires adjustment
    private static final double DEVIATION_THRESHOLD = 5.0;

    @Override
    public Map<String, Object> calculatePerformanceMetrics() {
        // Fetch all data
        List<Employee> employees = employeeRepository.findAll();
        List<Category> categories = categoryRepository.findAll();
        int totalEmployees = employees.size();

        // Get standard percentages from categories
        Map<Rating, Double> standardPercentages = new HashMap<>();
        for (Category category : categories) {
            standardPercentages.put(category.getRating(), category.getStandardPercentage());
        }

        // Count employees by rating
        Map<Rating, List<Employee>> employeesByRating = groupEmployeesByRating(employees);
        Map<Rating, Integer> actualCounts = calculateActualCounts(employeesByRating);

        // Calculate actual percentages
        Map<Rating, Double> actualPercentages = calculatePercentages(actualCounts, totalEmployees);

        // Calculate deviations
        Map<Rating, Double> deviations = calculateDeviations(actualPercentages, standardPercentages);

        // Determine required adjustments
        Map<Employee, Rating> suggestedAdjustments = determineAdjustments(
                employeesByRating, deviations, standardPercentages, totalEmployees
        );

        // Create appraisals for suggested adjustments
        createAppraisalsForAdjustments(suggestedAdjustments);

        // Prepare result
        Map<String, Object> result = new HashMap<>();
        result.put("totalEmployees", totalEmployees);
        result.put("actualPercentages", actualPercentages);
        result.put("standardPercentages", standardPercentages);
        result.put("deviations", deviations);
        result.put("suggestedAdjustments", suggestedAdjustments);

        return result;
    }

    private Map<Rating, List<Employee>> groupEmployeesByRating(List<Employee> employees) {
        Map<Rating, List<Employee>> employeesByRating = new HashMap<>();

        // Initialize lists for all ratings
        for (Rating rating : Rating.values()) {
            employeesByRating.put(rating, new ArrayList<>());
        }

        // Group employees by rating
        for (Employee employee : employees) {
            Rating rating = employee.getRating();
            employeesByRating.get(rating).add(employee);
        }

        // Sort employees within each rating by ID
        for (List<Employee> empList : employeesByRating.values()) {
            empList.sort(Comparator.comparing(Employee::getEmployeeId));
        }

        return employeesByRating;
    }

    private Map<Rating, Integer> calculateActualCounts(Map<Rating, List<Employee>> employeesByRating) {
        Map<Rating, Integer> counts = new HashMap<>();

        for (Rating rating : Rating.values()) {
            counts.put(rating, employeesByRating.get(rating).size());
        }

        return counts;
    }

    private Map<Rating, Double> calculatePercentages(Map<Rating, Integer> counts, int total) {
        Map<Rating, Double> percentages = new HashMap<>();

        for (Map.Entry<Rating, Integer> entry : counts.entrySet()) {
            double percentage = (entry.getValue() * 100.0) / total;
            percentages.put(entry.getKey(), percentage);
        }

        return percentages;
    }

    private Map<Rating, Double> calculateDeviations(
            Map<Rating, Double> actualPercentages,
            Map<Rating, Double> standardPercentages) {

        Map<Rating, Double> deviations = new HashMap<>();

        for (Rating rating : Rating.values()) {
            double actual = actualPercentages.getOrDefault(rating, 0.0);
            double standard = standardPercentages.getOrDefault(rating, 0.0);
            deviations.put(rating, actual - standard);
        }

        return deviations;
    }

    private Map<Employee, Rating> determineAdjustments(
            Map<Rating, List<Employee>> employeesByRating,
            Map<Rating, Double> deviations,
            Map<Rating, Double> standardPercentages,
            int totalEmployees) {

        Map<Employee, Rating> adjustments = new HashMap<>();

        // Sort ratings by deviation magnitude (most significant first)
        List<Rating> sortedRatings = new ArrayList<>(deviations.keySet());
        sortedRatings.sort((r1, r2) -> Double.compare(Math.abs(deviations.get(r2)), Math.abs(deviations.get(r1))));

        // Working copy of employee groups to track adjustments
        Map<Rating, List<Employee>> remainingEmployees = new HashMap<>();
        for (Rating rating : Rating.values()) {
            remainingEmployees.put(rating, new ArrayList<>(employeesByRating.get(rating)));
        }

        // Working copy of deviations to track changes
        Map<Rating, Double> workingDeviations = new HashMap<>(deviations);

        // Process ratings in order of deviation magnitude
        for (Rating currentRating : sortedRatings) {
            double deviation = workingDeviations.get(currentRating);

            // Skip if deviation is below threshold
            if (Math.abs(deviation) <= DEVIATION_THRESHOLD) {
                continue;
            }

            if (deviation > 0) {
                // Overrepresented - need to downgrade some employees
                handleOverrepresentedRating(
                        currentRating,
                        remainingEmployees,
                        workingDeviations,
                        adjustments,
                        totalEmployees
                );
            } else {
                // Underrepresented - need to upgrade employees from lower rating
                handleUnderrepresentedRating(
                        currentRating,
                        remainingEmployees,
                        workingDeviations,
                        adjustments,
                        totalEmployees
                );
            }
        }

        return adjustments;
    }

    private void handleOverrepresentedRating(
            Rating rating,
            Map<Rating, List<Employee>> remainingEmployees,
            Map<Rating, Double> workingDeviations,
            Map<Employee, Rating> adjustments,
            int totalEmployees) {

        double deviation = workingDeviations.get(rating);

        // Calculate how many employees to adjust
        int adjustCount = (int) Math.ceil((deviation / 100.0) * totalEmployees);

        // Find the best target rating for downgrade
        Rating targetRating = findBestTargetForDowngrade(rating, workingDeviations);

        if (targetRating == null) {
            return; // No suitable target found
        }

        List<Employee> candidates = remainingEmployees.get(rating);
        int adjusted = 0;

        for (int i = 0; i < candidates.size() && adjusted < adjustCount; i++) {
            Employee emp = candidates.get(i);

            if (appraisalExists(emp, targetRating)) {
                adjustments.put(emp, targetRating);

                // Update working data
                remainingEmployees.get(rating).remove(emp);

                // Calculate impact on deviations
                double pctChange = 100.0 / totalEmployees;
                workingDeviations.put(rating, workingDeviations.get(rating) - pctChange);
                workingDeviations.put(targetRating, workingDeviations.get(targetRating) + pctChange);

                adjusted++;
            }
        }
    }

    private void handleUnderrepresentedRating(
            Rating rating,
            Map<Rating, List<Employee>> remainingEmployees,
            Map<Rating, Double> workingDeviations,
            Map<Employee, Rating> adjustments,
            int totalEmployees) {

        double deviation = workingDeviations.get(rating);

        // Calculate how many employees to adjust
        int adjustCount = (int) Math.ceil((Math.abs(deviation) / 100.0) * totalEmployees);

        // Find the best source rating for upgrade
        Rating sourceRating = findBestSourceForUpgrade(rating, workingDeviations);

        if (sourceRating == null) {
            return; // No suitable source found
        }

        List<Employee> candidates = remainingEmployees.get(sourceRating);
        int adjusted = 0;

        for (int i = 0; i < candidates.size() && adjusted < adjustCount; i++) {
            Employee emp = candidates.get(i);

            if (appraisalExists(emp, rating)) {
                adjustments.put(emp, rating);

                // Update working data
                remainingEmployees.get(sourceRating).remove(emp);

                // Calculate impact on deviations
                double pctChange = 100.0 / totalEmployees;
                workingDeviations.put(sourceRating, workingDeviations.get(sourceRating) - pctChange);
                workingDeviations.put(rating, workingDeviations.get(rating) + pctChange);

                adjusted++;
            }
        }
    }

    private Rating findBestTargetForDowngrade(Rating rating, Map<Rating, Double> deviations) {
        Rating bestTarget = null;
        double bestDeviation = Double.MAX_VALUE;

        for (Rating target : Rating.values()) {
            // Only consider ratings that are adjacent and not extremely overrepresented
            if (isAdjacent(rating, target) &&
                    target.ordinal() < rating.ordinal() &&
                    deviations.get(target) < DEVIATION_THRESHOLD) {

                double targetDeviation = Math.abs(deviations.get(target));
                if (targetDeviation < bestDeviation) {
                    bestDeviation = targetDeviation;
                    bestTarget = target;
                }
            }
        }

        return bestTarget;
    }

    private Rating findBestSourceForUpgrade(Rating rating, Map<Rating, Double> deviations) {
        Rating bestSource = null;
        double bestDeviation = Double.MAX_VALUE;

        for (Rating source : Rating.values()) {
            // Only consider ratings that are adjacent and not extremely underrepresented
            if (isAdjacent(rating, source) &&
                    source.ordinal() < rating.ordinal() &&
                    deviations.get(source) > -DEVIATION_THRESHOLD) {

                double sourceDeviation = Math.abs(deviations.get(source));
                if (sourceDeviation < bestDeviation) {
                    bestDeviation = sourceDeviation;
                    bestSource = source;
                }
            }
        }

        return bestSource;
    }

    private boolean isAdjacent(Rating r1, Rating r2) {
        return Math.abs(r1.ordinal() - r2.ordinal()) == 1;
    }

    private void createAppraisalsForAdjustments(Map<Employee, Rating> adjustments) {
        for (Map.Entry<Employee, Rating> entry : adjustments.entrySet()) {
            Employee employee = entry.getKey();
            Rating suggestedRating = entry.getValue();

            if (appraisalExists(employee, suggestedRating)) {
                appraisalRepository.save(new Appraisal(employee, suggestedRating));
            }
        }
    }

    private boolean appraisalExists(Employee emp, Rating suggestedRating) {
        return appraisalRepository.findByEmployee(emp).stream()
                .noneMatch(appraisal -> appraisal.getSuggestedRating().equals(suggestedRating));
    }
}