package com.dev.identity;

import jakarta.xml.bind.DatatypeConverter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@Slf4j
class IdentityApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    public void encryptPassword() throws NoSuchAlgorithmException {
        String password = "123456";

        // MD5 Encryption
        MessageDigest md = MessageDigest.getInstance("MD5");

        // MD5 hashing round 1
        md.update(password.getBytes());
        byte[] digest = md.digest();
        String md5Hash = DatatypeConverter.printHexBinary(digest);

        log.info("MD5 round 1: {}", md5Hash);

        // MD5 hashing round 2
        md.update(password.getBytes());
        digest = md.digest();
        md5Hash = DatatypeConverter.printHexBinary(digest);

        log.info("MD5 round 2: {}", md5Hash);

        // BCrypt Encryption
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder(10);

        log.info("BCrypt round 1: {}", passwordEncoder.encode(password));
        log.info("BCrypt round 2: {}", passwordEncoder.encode(password));

    }
}
