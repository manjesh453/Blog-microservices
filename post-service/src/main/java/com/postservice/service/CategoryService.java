package com.postservice.service;

import com.postservice.dto.CategoryRequestDto;
import com.postservice.dto.CategoryResponseDto;
import com.postservice.shared.Status;

import java.util.List;

public interface CategoryService {
    public String createCategory(CategoryRequestDto categoryDto);

    public String updateCategory(CategoryRequestDto categoryDto, Long categoryId);

    public String deleteCategory(Long categoryId);

    public CategoryResponseDto getCategory(Long CategoryId);

    public List<CategoryResponseDto> getAllCategory();

    public List<CategoryResponseDto> getAllCategoryByStatus(Status status);
}
