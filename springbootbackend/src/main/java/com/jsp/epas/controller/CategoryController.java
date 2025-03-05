package com.jsp.epas.controller;

import com.jsp.epas.entity.Category;
import com.jsp.epas.requestdto.CategoryRequest;
import com.jsp.epas.responsedto.CategoryResponse;
import com.jsp.epas.service.CategoryService;
import com.jsp.epas.utility.ResponseStructure;
import com.jsp.epas.utility.RestResponseBuilder;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class CategoryController {

    private final RestResponseBuilder responseBuilder;
    private final CategoryService categoryService;

    @PostMapping("/category")
    public ResponseEntity<ResponseStructure<CategoryResponse>> addCategory(@RequestBody CategoryRequest categoryRequest) {
        CategoryResponse categoryResponse = categoryService.addCategory(categoryRequest);
        return responseBuilder.success(HttpStatus.CREATED, "Category Created", categoryResponse);
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<ResponseStructure<CategoryResponse>> findCategoryById(@PathVariable int id) {
        CategoryResponse categoryResponse = categoryService.findCategoryById(id);
        return responseBuilder.success(HttpStatus.FOUND, "Category Found", categoryResponse);
    }

    @GetMapping("/category")
    public ResponseEntity<ResponseStructure<List<CategoryResponse>>> findAllCategories() {
        List<CategoryResponse> categoryResponses = categoryService.findAllCategory();
        return responseBuilder.success(HttpStatus.OK, "Categories Retrieved", categoryResponses);
    }

    @PutMapping("/category/{id}")
    public ResponseEntity<ResponseStructure<CategoryResponse>> updateCategoryById(@PathVariable int id, @RequestBody CategoryRequest categoryRequest) {
        CategoryResponse categoryResponse = categoryService.updateCategoryById(id, categoryRequest);
        return responseBuilder.success(HttpStatus.OK, "Category Updated", categoryResponse);
    }

    @DeleteMapping("/category/{id}")
    public ResponseEntity<ResponseStructure<CategoryResponse>> deleteCategoryById(@PathVariable int id) {
        CategoryResponse categoryResponse = categoryService.deleteCategoryById(id);
        return responseBuilder.success(HttpStatus.OK, "Category Deleted", categoryResponse);
    }

}
