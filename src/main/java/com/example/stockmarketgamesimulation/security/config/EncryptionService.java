package com.example.stockmarketgamesimulation.security.config;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Service
public class EncryptionService {
    private final SecretKey secretKey;
    private final Cipher cipher;
    public EncryptionService() throws NoSuchAlgorithmException, NoSuchPaddingException {
        cipher = Cipher.getInstance("AES");
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);
        secretKey = keyGenerator.generateKey();
    }

    public String encrypt(String input) {
        try{
            byte[] plainText = input.getBytes();
            cipher.init(Cipher.ENCRYPT_MODE,secretKey);
            byte[] encryptedByte = cipher.doFinal(plainText);
            Base64.Encoder encoder = Base64.getEncoder();
            return encoder.encodeToString(encryptedByte);
        } catch(Exception e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR,"Error Generating token.");
        }
    }

    public String decrypt(String input) {
        try{
            Base64.Decoder decoder = Base64.getDecoder();
            byte[] encryptedByte = decoder.decode(input);
            cipher.init(Cipher.DECRYPT_MODE,secretKey);
            byte[] decryptedByte = cipher.doFinal(encryptedByte);
            return new String(decryptedByte);
        } catch (Exception e){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Key requires a refresh.");
        }
    }
}
