package com.postservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.postservice.conf.RabbitMQConfig;
import com.postservice.dto.PostRequestDto;
import com.postservice.dto.PostResponseDto;
import com.postservice.entity.Post;
import com.postservice.exception.DataNotFoundException;
import com.postservice.exception.ResourcenotFoundException;
import com.postservice.repo.PostRepo;
import com.postservice.service.FileService;
import com.postservice.service.PostService;
import com.postservice.shared.Category;
import com.postservice.shared.Status;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PostServiceImpl implements PostService {
    private final PostRepo postRepo;
    private final ModelMapper modelMapper;
        private final ObjectMapper objectMapper;
    private final FileService fileService;
    @Value("${project.image}")
    private String path;

    @Override
    public String createPost(PostRequestDto postDto, String fileName) {
        Post post = objectMapper.convertValue(postDto, Post.class);
        post.setImageName(fileName);
        post.setStatus(Status.ACTIVE);
        postRepo.save(post);
        return "Post has been successfully created";
    }

    @Override
    public String updatePost(PostRequestDto postDto, String fileName, Long postId) throws IOException {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourcenotFoundException("Post", "postId", postId));
        if (post.getUserId() != postDto.getUserId()) {
           throw new DataNotFoundException("You do not have permission to update this post");
        }
        fileService.deleteImage(path,post.getImageName());
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
        List<Post> posts = postRepo.findPostByStatus(Status.ACTIVE);
        return posts.stream().map(list -> modelMapper.map(list, PostResponseDto.class)).toList();
    }

    @Override
    public PostResponseDto getPostById(Long postId) {
        Post post = postRepo.findById(postId).orElseThrow(() -> new ResourcenotFoundException("Post", "postId", postId));
        return objectMapper.convertValue(post,PostResponseDto.class);
    }

    @Override
    public List<PostResponseDto> getPostsByCategoryForUsers(Category categoryId) {
        List<Post> posts = postRepo.findByCategoryIdAndStatus(categoryId,Status.ACTIVE);
        return posts.stream().map(list -> modelMapper.map(list, PostResponseDto.class)).toList();
    }

    @Override
    public List<PostResponseDto> getPostsByCategoryForAdmin(Category categoryId, Status status) {
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

    @Override
    public List<PostResponseDto> getPostsForUnauthorizedUser() {
        PageRequest pageRequest = PageRequest.of(0, 10);
        Page<Post> posts = postRepo.findAll(pageRequest);
        return posts.stream().map(list -> modelMapper.map(list, PostResponseDto.class)).toList();

    }
}
