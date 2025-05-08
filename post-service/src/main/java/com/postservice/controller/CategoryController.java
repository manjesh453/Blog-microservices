package com.postservice.controller;

import com.postservice.dto.CategoryRequestDto;
import com.postservice.dto.CategoryResponseDto;
import com.postservice.entity.Category;
import com.postservice.service.CategoryService;
import com.postservice.shared.Status;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/category/")
@AllArgsConstructor
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/add")
    public String addCategory(@RequestBody CategoryRequestDto requestDto) {
        return categoryService.createCategory(requestDto);
    }

    @PostMapping("/update/{categoryId}")
    public String updateCategory(@RequestBody CategoryRequestDto requestDto,@PathVariable Long categoryId) {
        return categoryService.updateCategory(requestDto,categoryId);
    }

    @GetMapping("/delete/{categoryId}")
    public String deleteCategory(@PathVariable Long categoryId) {
        return categoryService.deleteCategory(categoryId);
    }

    @GetMapping("/getCategoryById/{categoryId}")
    public CategoryResponseDto getCategoryById(@PathVariable Long categoryId) {
        return categoryService.getCategory(categoryId);
    }

    @GetMapping("/getAllCategory")
    public List<CategoryResponseDto> getAllCategory(){
        return categoryService.getAllCategory();
    }

    @GetMapping("/getCategoryByStatus/{status}")
    public List<CategoryResponseDto> getCategoryByStatus(@PathVariable String status){
        return categoryService.getAllCategoryByStatus(Status.valueOf(status));
    }
}
