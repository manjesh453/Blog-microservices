package com.userservice.service;


import com.userservice.entity.RefreshToken;

public interface RefreshService {
    RefreshToken createRefreshToken(String username);

    RefreshToken verifyExpiration(RefreshToken token);
}
