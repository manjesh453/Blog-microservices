package com.postservice.service.impl;

import com.postservice.dto.CategoryRequestDto;
import com.postservice.dto.CategoryResponseDto;
import com.postservice.entity.Category;
import com.postservice.exception.ResourcenotFoundException;
import com.postservice.repo.CategoryRepo;
import com.postservice.service.CategoryService;
import com.postservice.shared.Status;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {
    private final ModelMapper modelMapper;
    private final CategoryRepo categoryRepo;

    @Override
    public String createCategory(CategoryRequestDto categoryDto) {
        Category category = modelMapper.map(categoryDto, Category.class);
        category.setStatus(Status.ACTIVE);
        categoryRepo.save(category);
        return "Category has been created";
    }

    @Override
    public String updateCategory(CategoryRequestDto categoryDto, Long categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourcenotFoundException("Category", "categoryId", categoryId));
        category.setCategoryDescription(categoryDto.getCategoryDescription());
        category.setCategoryTitle(categoryDto.getCategoryTitle());
        categoryRepo.save(category);
        return "Category has been updated";
    }

    @Override
    public String deleteCategory(Long categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourcenotFoundException("Category", "categoryId", categoryId));
        category.setStatus(Status.DELETED);
        categoryRepo.save(category);
        return "Category has been deleted";
    }

    @Override
    public CategoryResponseDto getCategory(Long categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(() -> new ResourcenotFoundException("Category", "categoryId", categoryId));
        return modelMapper.map(category, CategoryResponseDto.class);
    }

    @Override
    public List<CategoryResponseDto> getAllCategory() {
        List<Category> listOfCategory = categoryRepo.findAll();
        return listOfCategory.stream().map(list -> modelMapper.map(list, CategoryResponseDto.class)).toList();
    }

    @Override
    public List<CategoryResponseDto> getAllCategoryByStatus(Status status) {
        List<Category> listOfCategory = categoryRepo.findByStatus(status);
        return listOfCategory.stream().map(list -> modelMapper.map(list, CategoryResponseDto.class)).toList();
    }
}
