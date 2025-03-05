package com.jsp.epas.service;

import com.jsp.epas.requestdto.CategoryRequest;
import com.jsp.epas.responsedto.CategoryResponse;

import java.util.List;

public interface CategoryService {


    CategoryResponse addCategory(CategoryRequest category);

    CategoryResponse findCategoryById(int id);

    List<CategoryResponse> findAllCategory();

    CategoryResponse updateCategoryById(int id, CategoryRequest category);

    CategoryResponse deleteCategoryById(int id);
}
