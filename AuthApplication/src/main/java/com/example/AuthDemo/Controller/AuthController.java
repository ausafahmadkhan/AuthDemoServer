package com.example.AuthDemo.Controller;

import com.example.AuthDemo.AuthRequest.TokenRequest;
import com.example.AuthDemo.AuthResponse.AuthPublicCert;
import com.example.AuthDemo.AuthResponse.ResponseModel;
import com.example.AuthDemo.AuthResponse.TokenResponse;
import com.example.AuthDemo.Service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/auth")
public class AuthController
{
    @Autowired
    private AuthService authService;

    @RequestMapping(value = "/getPublicKey", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<ResponseEntity<ResponseModel<AuthPublicCert>>> getPublicKey()
    {
        CompletableFuture<AuthPublicCert> authPublicKeyCompletableFuture = authService.getPublicKey();
        CompletableFuture<ResponseEntity<ResponseModel<AuthPublicCert>>> publicKey = authPublicKeyCompletableFuture
                    .thenApply(t -> new ResponseEntity<ResponseModel<AuthPublicCert>>(new ResponseModel<AuthPublicCert>(t), HttpStatus.OK));

        return publicKey;
    }

    @RequestMapping(value = "/generateToken", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public CompletableFuture<ResponseEntity<ResponseModel<TokenResponse>>> getToken(@RequestBody TokenRequest tokenRequest)
    {
        CompletableFuture<TokenResponse> completableFuture = authService.getToken(tokenRequest);
        return completableFuture.thenApply(t -> new ResponseEntity<ResponseModel<TokenResponse>>(new ResponseModel<TokenResponse>(t), HttpStatus.OK));
    }
}
