package com.jsp.epas.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class EmployeeNotFoundException extends  RuntimeException{
    private final String message;
}
