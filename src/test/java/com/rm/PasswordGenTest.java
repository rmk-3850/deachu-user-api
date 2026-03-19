package com.rm;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordGenTest {
    @Test
    void generatePw(){
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        String rawPassword = "Sozlsms187!";
        String encodedPassword = encoder.encode(rawPassword);
        
        System.out.println("================================");
        System.out.println("암호화된 비밀번호: " + encodedPassword);
        System.out.println("================================");
    }
}
