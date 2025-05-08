package com.userservice.security.service;

import com.userservice.security.helper.JwtAuthenticationResponse;
import com.userservice.security.helper.SignUpRequest;
import com.userservice.security.helper.SigninRequest;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;

public interface AuthenticationService {
    String signup(SignUpRequest request, String siteURL) throws MessagingException, UnsupportedEncodingException;

    JwtAuthenticationResponse signin(SigninRequest request);

    String forgetPassword(SigninRequest request, String siteURL) throws MessagingException, UnsupportedEncodingException;

    String changeUserPassword(String email, String siteURL) throws MessagingException, UnsupportedEncodingException;
}
