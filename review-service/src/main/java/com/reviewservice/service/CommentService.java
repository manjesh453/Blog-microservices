package com.reviewservice.service;

import com.reviewservice.dto.CommentRequestDto;
import com.reviewservice.dto.CommentResponseDto;

import java.util.List;

public interface CommentService {

    String addComment(CommentRequestDto comment);

    String update(CommentRequestDto comment, Long id);

    String deleteComment(Long id);

    List<CommentResponseDto> getComments();

    CommentResponseDto getComment(Long id);

    List<CommentResponseDto> getCommentsByPost(Long id);
}
