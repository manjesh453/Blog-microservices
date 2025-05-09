package com.userservice.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.userservice.circuitbreaker.ResillientServiceClient;
import com.userservice.dto.PostRequestDto;
import com.userservice.service.CretePost;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class CreatePostImpl implements CretePost {
    private final ResillientServiceClient restTemplate;
    private final ObjectMapper objectMapper;
    @Override
    public String createPost(MultipartFile image, PostRequestDto postDto) throws IOException {
        String postServiceUrl = "http://localhost:8080/api/post/add";
        String postDtoJson = objectMapper.writeValueAsString(postDto);

        ByteArrayResource imageResource = new ByteArrayResource(image.getBytes()) {
            @Override
            public String getFilename() {
                return image.getOriginalFilename();
            }
        };

        HttpHeaders jsonHeaders = new HttpHeaders();
        jsonHeaders.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<String> jsonPart = new HttpEntity<>(postDtoJson, jsonHeaders);

        MultiValueMap<String, Object> multipartBody = new LinkedMultiValueMap<>();
        multipartBody.add("image", imageResource);
        multipartBody.add("postDto", jsonPart);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        String jwt = getJwtFromContext();
        if (jwt != null) {
            headers.setBearerAuth(jwt);
        }

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(multipartBody, headers);

        ResponseEntity<String> response = restTemplate.callService(
                postServiceUrl,
                HttpMethod.POST,
                requestEntity,
                String.class
        );
        return response.getBody();
    }

    public String getJwtFromContext() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.getDetails() instanceof String jwt) {
            return jwt;
        }
        return null;
    }
}
