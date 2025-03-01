package com.jsp.epas.responsedto;

import com.jsp.epas.enums.Rating;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CategoryResponse {

    private int categoryId;
    private Rating rating;
    private double standardPercentage;
}
