package com.userservice.dto;

import com.userservice.shared.Category;
import lombok.Data;

@Data
public class PostRequestDto {
    private String title;

    private String content;

    private Long userId;

    private Category categoryId;
}
