package com.reviewservice.dto;

import lombok.Data;

@Data
public class CommentRequestDto {
    private String cmtName;

    private Long postId;

    private Long userId;
}
