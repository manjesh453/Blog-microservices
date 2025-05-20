package com.reviewservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reviewservice.Repo.CommentRepo;
import com.reviewservice.dto.CommentRequestDto;
import com.reviewservice.dto.CommentResponseDto;
import com.reviewservice.entity.Comment;
import com.reviewservice.exception.DataNotFoundException;
import com.reviewservice.exception.ResourcenotFoundException;
import com.reviewservice.service.CommentService;
import com.reviewservice.shared.Status;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepo commentRepo;
    private final ModelMapper modelMapper;
    private final ObjectMapper objectMapper;

    @Override
    public String addComment(CommentRequestDto commentDto) {
        Comment comment = objectMapper.convertValue(commentDto, Comment.class);
        comment.setStatus(Status.ACTIVE);
        commentRepo.save(comment);
        return "Comment added successfully";
    }

    @Override
    public String update(CommentRequestDto commentDto, Long id) {
        Comment comment = commentRepo.findById(id).orElseThrow(()->new ResourcenotFoundException("Comment","commentId",id));
        if(comment.getUserId()!= comment.getUserId()){
            throw new DataNotFoundException("You are not authorized to update this comment");
        }
        comment.setCmtName(commentDto.getCmtName());
        commentRepo.save(comment);
        return "Comment has been updated successfully";
    }

    @Override
    public String deleteComment(Long id) {
        Comment comment = commentRepo.findById(id).orElseThrow(()->new ResourcenotFoundException("Comment","commentId",id));
        if(comment.getUserId()!= comment.getUserId()){
            throw new DataNotFoundException("You are not authorized to delete this comment");
        }
        comment.setStatus(Status.DELETED);
        commentRepo.save(comment);
        return "Comment has been deleted successfully";
    }

    @Override
    public List<CommentResponseDto> getComments() {
        List<Comment> comments = commentRepo.findAll();
        return comments.stream().map(list->modelMapper.map(list,CommentResponseDto.class)).toList();
    }

    @Override
    public CommentResponseDto getComment(Long id) {
        Comment comment = commentRepo.findById(id).orElseThrow(()->new ResourcenotFoundException("Comment","commentId",id));
        return modelMapper.map(comment,CommentResponseDto.class);
    }

    @Override
    public List<CommentResponseDto> getCommentsByPost(Long id) {
        List<Comment> comments = commentRepo.findByPostId(id);
        return comments.stream().map(list->modelMapper.map(list,CommentResponseDto.class)).toList();
    }
}
