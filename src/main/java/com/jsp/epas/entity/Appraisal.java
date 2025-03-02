package com.jsp.epas.entity;

import com.jsp.epas.enums.Rating;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "appraisals")
public class Appraisal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int appraisalId;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee;

    @Enumerated(EnumType.STRING)
    private Rating suggestedRating;

    public Appraisal(Employee employee, Rating suggestedRating) {
        this.employee = employee;
        this.suggestedRating = suggestedRating;
    }

}
