package com.postservice.service;

import com.postservice.dto.PostRequestDto;
import com.postservice.dto.PostResponseDto;
import com.postservice.shared.Status;

import java.io.IOException;
import java.util.List;

public interface PostService {

    String createPost(PostRequestDto postDto, String fileName);

    String updatePost(PostRequestDto postDto,String fileName,Long postId) throws IOException;

    String deletePost(Long postId);

    List<PostResponseDto> getAllPost();

    PostResponseDto getPostById(Long postId);

    List<PostResponseDto> getPostsByCategoryForUsers(Long categoryId);

    List<PostResponseDto> getPostsByCategoryForAdmin(Long categoryId, Status status);

    List<PostResponseDto> getPostsByUser(Long userId);

    List<PostResponseDto> searchPostsByTitle(String keyword);

    void addLikeOnPost(String postId);
}
