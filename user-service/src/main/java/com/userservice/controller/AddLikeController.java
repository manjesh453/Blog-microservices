package com.userservice.controller;

import com.userservice.dto.PostRequestDto;
import com.userservice.service.AddReview;
import com.userservice.service.CretePost;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/user/react")
@RequiredArgsConstructor
public class AddLikeController {

    private final AddReview addReview;
    private final CretePost createPost;

    @GetMapping("/add/{postId}")
    public void addLike(@PathVariable Long postId) {
        addReview.addLike(postId);
    }

    @PostMapping("/post/add")
    public String createPost(@RequestParam("image") MultipartFile image, @RequestPart PostRequestDto postDto) throws IOException {
        return createPost.createPost(image, postDto);
    }
}
