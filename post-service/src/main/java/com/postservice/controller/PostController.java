package com.postservice.controller;


import com.postservice.dto.PostRequestDto;
import com.postservice.dto.PostResponseDto;
import com.postservice.service.FileService;
import com.postservice.service.PostService;
import com.postservice.shared.Status;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/post")
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;
    private final FileService fileService;
    @Value("${project.image}")
    private String path;

    @PostMapping("/add")
    public String createPost(@RequestParam("image") MultipartFile image, @RequestParam PostRequestDto postDto) throws IOException {
        String filename = fileService.uploadImage(path, image);
        return postService.createPost(postDto, filename);
    }

    @PostMapping("/update/{postId}")
    public String updatePost(@RequestPart PostRequestDto requestDto, @RequestParam("image") MultipartFile image,
                             @PathVariable Long postId) throws IOException {
        String filename = this.fileService.uploadImage(path, image);
        return postService.updatePost(requestDto, filename, postId);
    }

    @GetMapping("/delete/{postId}")
    public String deletePost(@PathVariable Long postId) {
        return postService.deletePost(postId);
    }

    @GetMapping("/getById/{postId}")
    public PostResponseDto getPostById(@PathVariable Long postId) {
        return postService.getPostById(postId);
    }

    @GetMapping("/getPostByCategoryForUser/{categoryId}")
    public List<PostResponseDto> getPostByCategoryForUsers(@PathVariable Long categoryId) {
        return postService.getPostsByCategoryForUsers(categoryId);
    }

    @GetMapping("/getPostByCategoryForAdmin/{categoryId}")
    public List<PostResponseDto> getPostByCategoryForAdmin(@PathVariable Long categoryId, @RequestBody String status) {
        return postService.getPostsByCategoryForAdmin(categoryId, Status.valueOf(status));
    }

    @GetMapping("/getPostByUser/{userId}")
    public List<PostResponseDto> getPostByUser(@PathVariable Long userId) {
        return postService.getPostsByUser(userId);
    }

    @GetMapping("/filterByTitle/{keyword}")
    public List<PostResponseDto> filterByTitle(@PathVariable String keyword) {
        return postService.searchPostsByTitle(keyword);
    }

    @GetMapping(value = "/image/{imageName}",produces=MediaType.IMAGE_JPEG_VALUE )
    public void downloadImage(@PathVariable("imageName") String imageName,HttpServletResponse response)throws IOException {
        InputStream resource=this.fileService.getResource(path, imageName);
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource,response.getOutputStream());
    }

    @GetMapping("/postForUnauthorized")
    public List<PostResponseDto> getPostForUnauthorizedUser() {
        return postService.getPostsForUnauthorizedUser();
    }
}
