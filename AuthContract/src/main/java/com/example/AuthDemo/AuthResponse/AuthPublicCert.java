package com.example.AuthDemo.AuthResponse;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthPublicCert
{
    private String publicKey;
    private String alg;
}
