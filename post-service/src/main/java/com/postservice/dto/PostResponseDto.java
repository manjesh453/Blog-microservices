package com.postservice.dto;

import lombok.Data;

import java.util.Date;

@Data
public class PostResponseDto {
    private Long id;

    private String title;

    private String content;

    private String imageName;

    private Long userId;

    private Long categoryId;

    private Long likes;

    private Date createdDate;
}
