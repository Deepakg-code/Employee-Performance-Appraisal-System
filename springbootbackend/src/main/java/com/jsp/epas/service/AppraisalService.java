package com.jsp.epas.service;

import com.jsp.epas.entity.Appraisal;
import com.jsp.epas.enums.Rating;

import java.util.List;

public interface AppraisalService {
    List<Appraisal> getAllAppraisals();

    List<Appraisal> getAppraisalsByEmployeeId(int employeeId);

    Appraisal createAppraisal(int employeeId, Rating suggestedRating);

    void deleteAppraisal(int appraisalId);
}
