package com.jsp.epas.controller;

import com.jsp.epas.entity.Appraisal;
import com.jsp.epas.enums.Rating;
import com.jsp.epas.service.AppraisalService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@AllArgsConstructor
public class AppraisalController {

    private final AppraisalService appraisalService;

    @GetMapping("/appraisal")
    public ResponseEntity<List<Appraisal>> getAllAppraisals() {
        return ResponseEntity.ok(appraisalService.getAllAppraisals());
    }

    @GetMapping("/appraisal/employee/{employeeId}")
    public ResponseEntity<List<Appraisal>> getAppraisalsByEmployee(@PathVariable int employeeId) {
        return ResponseEntity.ok(appraisalService.getAppraisalsByEmployeeId(employeeId));
    }

    @PostMapping("/appraisal/{employeeId}")
    public ResponseEntity<Appraisal> createAppraisal(@PathVariable int employeeId, @RequestBody Map<String, String> request) {
        Rating suggestedRating = Rating.valueOf(request.get("suggestedRating"));
        return ResponseEntity.ok(appraisalService.createAppraisal(employeeId, suggestedRating));
    }

    @DeleteMapping("/appraisal/{appraisalId}")
    public ResponseEntity<Void> deleteAppraisal(@PathVariable int appraisalId) {
        appraisalService.deleteAppraisal(appraisalId);
        return ResponseEntity.noContent().build();
    }
}
