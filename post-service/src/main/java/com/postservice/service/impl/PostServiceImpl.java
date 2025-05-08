package com.postservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.postservice.conf.RabbitMQConfig;
import com.postservice.dto.CategoryResponseDto;
import com.postservice.dto.PostRequestDto;
import com.postservice.dto.PostResponseDto;
import com.postservice.entity.Post;
import com.postservice.exception.DataNotFoundException;
import com.postservice.exception.ResourcenotFoundException;
import com.postservice.repo.PostRepo;
import com.postservice.service.CategoryService;
import com.postservice.service.PostService;
import com.postservice.shared.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {
    private final PostRepo postRepo;
    private final ModelMapper modelMapper;
    private final CategoryService categoryService;
    private final ObjectMapper objectMapper;

    @Override
    public String createPost(PostRequestDto postDto, String fileName) {
        CategoryResponseDto category = categoryService.getCategory(postDto.getCategoryId());
        if (category == null) {
            throw new DataNotFoundException("Category not found");
        }
        Post post = objectMapper.convertValue(postDto, Post.class);
        post.setImageName(fileName);
        post.setStatus(Status.ACTIVE);
        postRepo.save(post);
        return "Post has been successfully created";
    }

    @Override
    public String updatePost(PostRequestDto postDto, String fileName, Long postId) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourcenotFoundException("Post", "postId", postId));
        if (post.getUserId() != postDto.getUserId()) {
           throw new DataNotFoundException("You do not have permission to update this post");
        }
        post.setTitle(postDto.getTitle());
        post.setContent(postDto.getContent());
        post.setImageName(fileName);
        postRepo.save(post);
        return "Post has been successfully updated";
    }

    @Override
    public String deletePost(Long postId) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourcenotFoundException("Post", "postId", postId));
        post.setStatus(Status.DELETED);
        postRepo.save(post);
        return "Post has been successfully deleted";
    }

    @Override
    public List<PostResponseDto> getAllPost() {
        List<Post> posts = postRepo.findAll();
        return posts.stream().map(list -> modelMapper.map(list, PostResponseDto.class)).toList();
    }

    @Override
    public PostResponseDto getPostById(Long postId) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourcenotFoundException("Post", "postId", postId));
        return objectMapper.convertValue(post,PostResponseDto.class);
    }

    @Override
    public List<PostResponseDto> getPostsByCategoryForUsers(Long categoryId) {
        List<Post> posts = postRepo.findByCategoryIdAndStatus(categoryId,Status.ACTIVE);
        return posts.stream().map(list -> modelMapper.map(list, PostResponseDto.class)).toList();
    }

    @Override
    public List<PostResponseDto> getPostsByCategoryForAdmin(Long categoryId, Status status) {
        List<Post> posts = postRepo.findByCategoryIdAndStatus(categoryId,status);
        return posts.stream().map(list -> modelMapper.map(list, PostResponseDto.class)).toList();
    }

    @Override
    public List<PostResponseDto> getPostsByUser(Long userId) {
        List<Post> posts = postRepo.findByUserId(userId);
        return posts.stream().map(list -> modelMapper.map(list, PostResponseDto.class)).toList();
    }

    @Override
    public List<PostResponseDto> searchPostsByTitle(String keyword) {
        List<Post> posts = postRepo.findByKeyword(keyword);
        return posts.stream().map(list -> modelMapper.map(list, PostResponseDto.class)).toList();
    }

    @Override
    @RabbitListener(queues = RabbitMQConfig.QUEUE_NAME)
    public void addLikeOnPost(String message) {
     try {
         long postId=Long.parseLong(message);
         Post post = postRepo.findById(postId).orElseThrow(() -> new ResourcenotFoundException("Post", "postId", postId));
         post.setLikes(post.getLikes() + 1);
         postRepo.save(post);
     }catch (Exception e) {
         log.error("Error occur while performing add operation "+e.getMessage());
     }
    }
}
