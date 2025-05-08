package com.postservice.dto;

import lombok.Data;

@Data
public class PostResponseDto {
    private Long id;

    private String title;

    private String content;

    private String imageName;

    private Long userId;

    private Long categoryId;

    private Long likes;
}
