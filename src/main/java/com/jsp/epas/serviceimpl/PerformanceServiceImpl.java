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

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class PerformanceServiceImpl implements PerformanceService {

    private final EmployeeRepository employeeRepository;
    private final CategoryRepository categoryRepository;
    private final AppraisalRepository appraisalRepository;

    public Map<String, Object> calculatePerformanceMetrics() {
        List<Employee> employees = employeeRepository.findAll();
        List<Category> categories = categoryRepository.findAll();

        Map<Rating, Integer> actualCounts = new HashMap<>();
        Map<Rating, Double> standardPercentages = new HashMap<>();
        Map<Rating, Double> deviations = new HashMap<>();

        // Initialize category counts
        for (Category category : categories) {
            actualCounts.put(category.getRating(), 0);
            standardPercentages.put(category.getRating(), category.getStandardPercentage());
        }

        // Count employees per category
        for (Employee employee : employees) {
            actualCounts.put(employee.getRating(), actualCounts.get(employee.getRating()) + 1);
        }

        // Calculate actual percentages
        int totalEmployees = employees.size();
        Map<Rating, Double> actualPercentages = actualCounts.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> (entry.getValue() * 100.0) / totalEmployees
                ));

        // Calculate deviations
        Map<Employee, Rating> suggestedAdjustments = new HashMap<>();
        deviations = actualPercentages.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        entry -> entry.getValue() - standardPercentages.getOrDefault(entry.getKey(), 0.0)
                ));

        deviations.entrySet().stream()
                .sorted((a, b) -> Double.compare(Math.abs(b.getValue()), Math.abs(a.getValue()))) // Prioritize larger deviations
                .forEach(entry -> {
                    Rating rating = entry.getKey();
                    double deviation = entry.getValue();
                    int adjustCount = Math.max(1, (int) ((Math.abs(deviation) / 100) * totalEmployees));

                    if (deviation > 5.0) { // Overrepresented category (downgrade needed)
                        List<Employee> employeesToAdjust = employees.stream()
                                .filter(emp -> emp.getRating() == rating)
                                .sorted(Comparator.comparing(Employee::getEmployeeId)) // Lower ID first
                                .limit(adjustCount)
                                .toList();

                        for (Employee emp : employeesToAdjust) {
                            Rating suggestedRating = rating.ordinal() > 0 ? Rating.values()[rating.ordinal() - 1] : rating;
                            if (suggestedRating != rating && appraisalExists(emp, suggestedRating) && !suggestedAdjustments.containsKey(emp)) {
                                suggestedAdjustments.put(emp, suggestedRating);
                                appraisalRepository.save(new Appraisal(emp, suggestedRating));
                            }
                        }
                    }

                    if (deviation < -5.0) {
                        List<Employee> employeesToAdjust = employees.stream()
                                .filter(emp -> emp.getRating().ordinal() == rating.ordinal() - 1) // Upgrade only from adjacent lower category
                                .sorted(Comparator.comparing(Employee::getEmployeeId)) // Lower ID first
                                .limit(adjustCount)
                                .toList();

                        for (Employee emp : employeesToAdjust) {
                            if (rating != emp.getRating() && appraisalExists(emp, rating) && !suggestedAdjustments.containsKey(emp)) {
                                suggestedAdjustments.put(emp, rating);
                                appraisalRepository.save(new Appraisal(emp, rating));
                            }
                        }
                    }
                });

        // Return performance evaluation result including suggestions
        Map<String, Object> result = new HashMap<>();
        result.put("totalEmployees", totalEmployees);
        result.put("actualPercentages", actualPercentages);
        result.put("standardPercentages", standardPercentages);
        result.put("deviations", deviations);
        result.put("suggestedAdjustments", suggestedAdjustments);
        return result;
    }

    private boolean appraisalExists(Employee emp, Rating suggestedRating) {
        return appraisalRepository.findByEmployee(emp).stream()
                .noneMatch(appraisal -> appraisal.getSuggestedRating() == suggestedRating);
    }
}
