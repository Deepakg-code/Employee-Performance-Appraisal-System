package com.jsp.epas.serviceimpl;

import com.jsp.epas.entity.Category;
import com.jsp.epas.exception.CategoryNotFoundException;
import com.jsp.epas.repository.CategoryRepository;
import com.jsp.epas.requestdto.CategoryRequest;
import com.jsp.epas.responsedto.CategoryResponse;
import com.jsp.epas.service.CategoryService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;

    private CategoryResponse mapToCategoryResponse(Category category) {
        return CategoryResponse.builder()
                .categoryId(category.getCategoryId())
                .rating(category.getRating())
                .standardPercentage(category.getStandardPercentage())
                .build();
    }

    @Override
    public CategoryResponse addCategory(CategoryRequest categoryRequest) {

        boolean exists = categoryRepository.existsByRating(categoryRequest.getRating());
        if (exists) {
            throw new IllegalArgumentException("Category with rating " + categoryRequest.getRating() + " already exists.");
        }

        Category category = Category.builder()
                .rating(categoryRequest.getRating())
                .standardPercentage(categoryRequest.getStandardPercentage())
                .build();
        Category savedCategory = categoryRepository.save(category);
        return mapToCategoryResponse(savedCategory);
    }


    @Override
    public CategoryResponse findCategoryById(int id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        return mapToCategoryResponse(category);
    }

    @Override
    public List<CategoryResponse> findAllCategory() {
        List<Category> categories = categoryRepository.findAll();
        return categories.stream()
                .map(this::mapToCategoryResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CategoryResponse updateCategoryById(int id, CategoryRequest categoryRequest) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        category.setRating(categoryRequest.getRating());
        category.setStandardPercentage(categoryRequest.getStandardPercentage());
        Category updatedCategory = categoryRepository.save(category);
        return mapToCategoryResponse(updatedCategory);
    }

    @Override
    public CategoryResponse deleteCategoryById(int id) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException("Category not found"));
        categoryRepository.delete(category);
        return mapToCategoryResponse(category);
    }

}