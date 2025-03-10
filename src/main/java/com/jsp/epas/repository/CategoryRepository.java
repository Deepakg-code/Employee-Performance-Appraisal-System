package com.jsp.epas.repository;

import com.jsp.epas.entity.Category;
import com.jsp.epas.enums.Rating;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
    boolean existsByRating(Rating rating);
}
