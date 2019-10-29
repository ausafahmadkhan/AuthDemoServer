package com.example.AuthDemo.Service;

import com.example.AuthDemo.AuthRequest.TokenRequest;
import com.example.AuthDemo.AuthResponse.AuthPublicCert;
import com.example.AuthDemo.AuthResponse.TokenResponse;

import java.util.concurrent.CompletableFuture;

public interface AuthService
{
    CompletableFuture<AuthPublicCert> getPublicKey();
    CompletableFuture<TokenResponse> getToken(TokenRequest tokenRequest);
}
