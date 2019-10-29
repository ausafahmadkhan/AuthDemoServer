package com.example.AuthDemo.Service;

import org.springframework.stereotype.Component;

import java.security.*;

@Component
public class KeyPairProvider
{
    private KeyPairGenerator keyPairGenerator;
    private KeyPair keyPair;
    private PrivateKey privateKey;
    private PublicKey publicKey;

    public KeyPairProvider() throws NoSuchAlgorithmException
    {
        this.keyPairGenerator = KeyPairGenerator.getInstance("RSA");
        this.keyPairGenerator.initialize(1048);
        this.keyPair = this.keyPairGenerator.genKeyPair();
        this.privateKey = this.keyPair.getPrivate();
        this.publicKey = this.keyPair.getPublic();
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }
}
