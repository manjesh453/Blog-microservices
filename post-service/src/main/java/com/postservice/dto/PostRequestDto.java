package com.postservice.dto;

import com.postservice.shared.Category;
import lombok.Data;

@Data
public class PostRequestDto {
    private String title;

    private String content;

    private Long userId;

    private Category categoryId;
}
