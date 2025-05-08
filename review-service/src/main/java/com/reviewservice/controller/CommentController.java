package com.reviewservice.controller;

import com.reviewservice.dto.CommentRequestDto;
import com.reviewservice.dto.CommentResponseDto;
import com.reviewservice.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/add")
    public String addComment(@RequestBody CommentRequestDto requestDto) {
        return commentService.addComment(requestDto);
    }

    @PostMapping("/update/{id}")
    public String updateComment(@RequestBody CommentRequestDto requestDto, @PathVariable Long id) {
        return commentService.update(requestDto, id);
    }

    @GetMapping("/delete/{id}")
    public String deleteComment(@PathVariable Long id) {
        return commentService.deleteComment(id);
    }

    @GetMapping("/getCommentById/{id}")
    public CommentResponseDto getComment(@PathVariable Long id) {
        return commentService.getComment(id);
    }

    @GetMapping("/getAllComments")
    public List<CommentResponseDto> getAllComments() {
        return commentService.getComments();
    }

    @GetMapping("/getCommentByPost/{postId}")
    public List<CommentResponseDto> getCommentsByPost(@PathVariable Long postId) {
        return commentService.getCommentsByPost(postId);
    }
}
