package com.jsp.epas.controller;

import com.jsp.epas.service.EmployeeService;
import com.jsp.epas.requestdto.EmployeeRequest;
import com.jsp.epas.responsedto.EmployeeResponse;
import com.jsp.epas.utility.ResponseStructure;
import com.jsp.epas.utility.RestResponseBuilder;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class EmployeeController {

    private final RestResponseBuilder responseBuilder;
    private final EmployeeService employeeService;

    @PostMapping("/employee")
    public ResponseEntity<ResponseStructure<EmployeeResponse>> addEmployee(@RequestBody EmployeeRequest employeeRequest){
        EmployeeResponse  employeeResponse = employeeService.addEmployee(employeeRequest);
        return responseBuilder.success(HttpStatus.CREATED, "Employee Created", employeeResponse);
    }

    @GetMapping("/employee/{employeeId}")
    public ResponseEntity<ResponseStructure<EmployeeResponse>> findEmployeeById(@PathVariable int employeeId){
        EmployeeResponse  employeeResponse = employeeService.findEmployeeById(employeeId);
        return responseBuilder.success(HttpStatus.FOUND, "Employee Found", employeeResponse);
    }

    @GetMapping("/employees")
    public ResponseEntity<ResponseStructure<List<EmployeeResponse>>> findAllEmployee(){
        List<EmployeeResponse> employeeResponse = employeeService.findAllEmployee();
        return responseBuilder.success(HttpStatus.OK, "Employees Retrieved", employeeResponse);
    }

    @PutMapping("/employee/{employeeId}")
    public ResponseEntity<ResponseStructure<EmployeeResponse>> updateEmployeeById(@PathVariable int employeeId, @RequestBody EmployeeRequest employeeRequest){
        EmployeeResponse  employeeResponse = employeeService.updateEmployeeById(employeeId, employeeRequest);
        return responseBuilder.success(HttpStatus.OK, "Employee Updated", employeeResponse);
    }

    @DeleteMapping("/employee/{employeeId}")
    public ResponseEntity<ResponseStructure<EmployeeResponse>> updateEmployeeById(@PathVariable int employeeId){
        EmployeeResponse  employeeResponse = employeeService.deleteEmployeeById(employeeId);
        return responseBuilder.success(HttpStatus.OK, "Employee Deleted", employeeResponse);
    }




}
