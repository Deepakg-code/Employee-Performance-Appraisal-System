package com.jsp.epas.service;

import com.jsp.epas.requestdto.EmployeeRequest;
import com.jsp.epas.responsedto.EmployeeResponse;

import java.util.List;

public interface EmployeeService {

    EmployeeResponse addEmployee(EmployeeRequest employeeRequest);

    EmployeeResponse findEmployeeById(int employeeId);

    List<EmployeeResponse> findAllEmployee();

    EmployeeResponse updateEmployeeById(int employeeId, EmployeeRequest employeeRequest);

    void deleteEmployeeById(int employeeId);

    EmployeeResponse updateEmployeeRating(int id, String newRating);
}
