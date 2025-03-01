package com.jsp.epas.responsedto;

import com.jsp.epas.enums.Rating;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeResponse {

    private int employeeId;
    private String employeeName;
    private Rating rating;
}
