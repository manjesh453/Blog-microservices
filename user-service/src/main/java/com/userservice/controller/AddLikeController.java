package com.userservice.controller;

import com.userservice.service.AddReview;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/react")
@RequiredArgsConstructor
public class AddLikeController {

    private final AddReview addReview;

    @GetMapping("/add/{postId}")
    public void addLike(@PathVariable Long postId){
        addReview.addLike(postId);
    }
}
