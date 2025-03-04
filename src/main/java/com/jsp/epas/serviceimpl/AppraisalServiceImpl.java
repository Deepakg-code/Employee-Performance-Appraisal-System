package com.jsp.epas.serviceimpl;

import com.jsp.epas.entity.Appraisal;
import com.jsp.epas.entity.Employee;
import com.jsp.epas.enums.Rating;
import com.jsp.epas.exception.EmployeeNotFoundException;
import com.jsp.epas.repository.AppraisalRepository;
import com.jsp.epas.repository.EmployeeRepository;
import com.jsp.epas.service.AppraisalService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AppraisalServiceImpl implements AppraisalService {

    private final AppraisalRepository appraisalRepository;
    private final EmployeeRepository employeeRepository;

    public List<Appraisal> getAllAppraisals() {
        return appraisalRepository.findAll();
    }

    public List<Appraisal> getAppraisalsByEmployeeId(int employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        return appraisalRepository.findByEmployee(employee);
    }

    public Appraisal createAppraisal(int employeeId, Rating suggestedRating) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));

        Optional<Appraisal> existingAppraisal = appraisalRepository.findByEmployee(employee)
                .stream()
                .findFirst();

        if (existingAppraisal.isPresent()) {
            Appraisal appraisal = existingAppraisal.get();
            appraisal.setSuggestedRating(suggestedRating);
            System.out.println("Updating appraisal for " + employee.getEmployeeName() + " → " + suggestedRating);
            return appraisalRepository.save(appraisal);
        } else {
            System.out.println("Creating new appraisal for " + employee.getEmployeeName() + " → " + suggestedRating);
            Appraisal newAppraisal = new Appraisal(employee, suggestedRating);
            return appraisalRepository.save(newAppraisal);
        }
    }

    public void deleteAppraisal(int appraisalId) {
        appraisalRepository.deleteById(appraisalId);
    }

}
