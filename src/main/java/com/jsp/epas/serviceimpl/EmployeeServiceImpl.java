package com.jsp.epas.serviceimpl;

import com.jsp.epas.entity.Employee;
import com.jsp.epas.enums.Rating;
import com.jsp.epas.exception.EmployeeNotFoundException;
import com.jsp.epas.repository.AppraisalRepository;
import com.jsp.epas.repository.EmployeeRepository;
import com.jsp.epas.requestdto.EmployeeRequest;
import com.jsp.epas.responsedto.EmployeeResponse;
import com.jsp.epas.service.EmployeeService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final AppraisalRepository appraisalRepository;

    private Employee mapToEmployee(EmployeeRequest employeeRequest, Employee employee) {
        employee.setEmployeeName(employeeRequest.getEmployeeName());
        employee.setRating(Rating.valueOf(String.valueOf(employeeRequest.getRating())));
        return employee;
    }

    private EmployeeResponse mapToEmployeeResponse(Employee employee) {
        return EmployeeResponse.builder()
                .employeeId(employee.getEmployeeId())
                .employeeName(employee.getEmployeeName())
                .rating(Rating.valueOf(String.valueOf(employee.getRating())))
                .build();
    }

    @Override
    public EmployeeResponse addEmployee(EmployeeRequest employeeRequest) {
        Employee employee = this.mapToEmployee(employeeRequest, new Employee());
        employee = employeeRepository.save(employee);
        return this.mapToEmployeeResponse(employee);
    }

    @Override
    public EmployeeResponse findEmployeeById(int employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(()-> new EmployeeNotFoundException("Employee not found"));
        return this.mapToEmployeeResponse(employee);
    }

    @Override
    public List<EmployeeResponse> findAllEmployee() {
        List<Employee> employees = employeeRepository.findAll();
        return employees.stream()
                .map(this::mapToEmployeeResponse)
                .collect(Collectors.toList());
    }

    @Override
    public EmployeeResponse updateEmployeeById(int employeeId, EmployeeRequest employeeRequest) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found by id"));
        mapToEmployee(employeeRequest, employee);
        employee = employeeRepository.save(employee);
        return mapToEmployeeResponse(employee);
    }

    @Transactional
    @Override
    public void deleteEmployeeById(int employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));

        appraisalRepository.deleteByEmployee(employee);

        employeeRepository.delete(employee);
    }


    @Override
    public EmployeeResponse updateEmployeeRating(int employeeId, String newRating) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found"));

        employee.setRating(Rating.valueOf(newRating));
        employeeRepository.save(employee);

        return mapToEmployeeResponse(employee);
    }



}
