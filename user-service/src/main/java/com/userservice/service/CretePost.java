package com.userservice.service;

import com.userservice.dto.PostRequestDto;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface CretePost {
    String createPost(MultipartFile image, PostRequestDto postDto) throws IOException;
}
