package com.reviewservice.dto;

import com.reviewservice.shared.Status;
import lombok.Data;

@Data
public class CommentResponseDto {
    private Long id;

    private String cmtName;

    private Status status;

    private Long postId;

    private Long userId;
}
