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
public class CategoryRequest {

    private Rating rating;
    private double standardPercentage;
}
