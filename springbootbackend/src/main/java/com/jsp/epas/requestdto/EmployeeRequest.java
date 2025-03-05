package com.jsp.epas.requestdto;

import com.jsp.epas.enums.Rating;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeRequest {

    private String employeeName;
    private Rating rating;
}
