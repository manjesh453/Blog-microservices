package com.userservice.controller;

import com.userservice.dto.RefreshRequestDto;
import com.userservice.entity.RefreshToken;
import com.userservice.repo.RefreshRepo;
import com.userservice.security.helper.JwtAuthenticationResponse;
import com.userservice.security.service.JwtService;
import com.userservice.service.RefreshService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/token")
@RequiredArgsConstructor
public class RefreshTokenController {

    private final RefreshService refreshTokenService;

    private final RefreshRepo refreshRepo;

    private final JwtService jwtService;

    @PostMapping("/refreshToken")
    public JwtAuthenticationResponse refreshToken(@RequestBody RefreshRequestDto refreshTokenRequestDTO){
        return refreshRepo.findByToken(refreshTokenRequestDTO.getToken())
                .map(refreshTokenService::verifyExpiration)
                .map(RefreshToken::getCustomer)
                .map(userInfo -> {
                    String accessToken = jwtService.generateToken(userInfo);
                    return JwtAuthenticationResponse.builder()
                            .token(accessToken)
                            .refreshToken(refreshTokenRequestDTO.getToken()).build();
                }).orElseThrow(() ->new RuntimeException("Refresh Token is not in DB..!!"));
    }
}
