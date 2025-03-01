package com.jsp.epas.serviceimpl;

import com.jsp.epas.entity.Employee;
import com.jsp.epas.exception.EmployeeNotFoundException;
import com.jsp.epas.repository.EmployeeRepository;
import com.jsp.epas.requestdto.EmployeeRequest;
import com.jsp.epas.responsedto.EmployeeResponse;
import com.jsp.epas.service.EmployeeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private Employee mapToEmployee(EmployeeRequest employeeRequest, Employee employee) {
        employee.setEmployeeName(employeeRequest.getEmployeeName());
        employee.setRating(employeeRequest.getRating());
        return employee;
    }

    private EmployeeResponse mapToEmployeeResponse(Employee employee) {
        return EmployeeResponse.builder()
                .employeeId(employee.getEmployeeId())
                .employeeName(employee.getEmployeeName())
                .rating(employee.getRating())
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

    @Override
    public EmployeeResponse deleteEmployeeById(int employeeId) {
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee not found by id"));
        employeeRepository.deleteById(employee.getEmployeeId());
        return mapToEmployeeResponse(employee);
    }


}
