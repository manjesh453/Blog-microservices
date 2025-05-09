package com.userservice.dto;

import lombok.Data;

@Data
public class PostRequestDto {
    private String title;

    private String content;

    private Long userId;

    private Long categoryId;
}
