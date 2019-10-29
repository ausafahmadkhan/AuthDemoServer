package com.example.AuthDemo.Service;

import com.example.AuthDemo.AuthRequest.TokenRequest;
import com.example.AuthDemo.AuthResponse.AuthPublicCert;
import com.example.AuthDemo.AuthResponse.TokenResponse;
import com.example.AuthDemo.Persistence.Model.UserDAO;
import com.example.AuthDemo.Persistence.Repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.util.Base64;
import java.util.Date;
import java.util.concurrent.CompletableFuture;

@Service
public class AuthServiceImpl implements AuthService
{
    @Autowired
    private KeyPairProvider keyPairProvider;

    @Autowired
    private UserRepository userRepository;

    @Override
    @Async
    public CompletableFuture<AuthPublicCert> getPublicKey()
    {
        AuthPublicCert authPublicCert = new AuthPublicCert();
        PublicKey publicKey = keyPairProvider.getPublicKey();
        byte[] bytes = publicKey.getEncoded();
        authPublicCert.setPublicKey(Base64.getEncoder().encodeToString(bytes));
        authPublicCert.setAlg(publicKey.getAlgorithm());
        return CompletableFuture.completedFuture(authPublicCert);
    }

    @Override
    @Async
    public CompletableFuture<TokenResponse> getToken(TokenRequest tokenRequest)
    {
        UserDAO userDAO = userRepository.findUserByUserName(tokenRequest.getUsername())
                .orElseThrow(() -> new IllegalArgumentException("User not present"));

        if (!tokenRequest.getPassword().equals(userDAO.getPassword()))
            throw new IllegalArgumentException("Invalid Password");

        String token = createToken(userDAO);
        TokenResponse tokenResponse = new TokenResponse(token);

        return CompletableFuture.completedFuture(tokenResponse);
    }

    private String createToken(UserDAO userDAO)
    {
        Claims claims = Jwts.claims().setIssuer("AuthDemo")
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(System.currentTimeMillis() + (3 * 60 * 60 * 1000)));

        claims.put("username", userDAO.getUsername());
        claims.put("email", userDAO.getEmail());

        return   Jwts
                .builder()
                .setClaims(claims)
                .signWith(SignatureAlgorithm.RS512, keyPairProvider.getPrivateKey())
                .compact();
    }
}
