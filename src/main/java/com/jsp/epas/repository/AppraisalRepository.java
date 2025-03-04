package com.jsp.epas.repository;

import com.jsp.epas.entity.Appraisal;
import com.jsp.epas.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppraisalRepository extends JpaRepository<Appraisal, Integer> {
    List<Appraisal> findByEmployee(Employee employee);

    void deleteByEmployee(Employee employee);
}
