package com.jsp.epas.exceptionhandler;

import com.jsp.epas.exception.CategoryNotFoundException;
import com.jsp.epas.utility.ErrorStructure;
import com.jsp.epas.utility.RestResponseBuilder;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@AllArgsConstructor
public class CategoryNotFoundHandler {

    private final RestResponseBuilder responseBuilder;

    @ExceptionHandler(CategoryNotFoundException.class)
    public<T> ResponseEntity<ErrorStructure<String>> handleRatingNotFound(CategoryNotFoundException ex){
        return responseBuilder.error(HttpStatus.NOT_FOUND, ex.getMessage(), "Employee Not Found By given Id");
    }

}
