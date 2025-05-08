package com.postservice.dto;

import com.postservice.shared.Status;
import lombok.Data;

@Data
public class CategoryRequestDto {
    private String categoryTitle;
    private String categoryDescription;
}
