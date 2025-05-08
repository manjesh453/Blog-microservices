package com.userservice.service.impl;

import com.userservice.config.MessagePublisher;
import com.userservice.service.AddReview;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AddReviewImpl implements AddReview {
    private final MessagePublisher messagePublisher;

    @Override
    public void addLike(Long postId) {
        messagePublisher.sendMessage(String.valueOf(postId));
    }
}
