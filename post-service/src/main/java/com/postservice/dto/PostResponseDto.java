package com.postservice.dto;

import com.postservice.shared.Category;
import lombok.Data;

import java.util.Date;

@Data
public class PostResponseDto {
    private Long id;

    private String title;

    private String content;

    private String imageName;

    private Long userId;

    private Category categoryId;

    private Long likes;

    private Date createdDate;
}
