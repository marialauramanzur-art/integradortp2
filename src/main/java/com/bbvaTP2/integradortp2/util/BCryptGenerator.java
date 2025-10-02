package com.bbvaTP2.integradortp2.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class BCryptGenerator {
    public static void main(String[] args) {
        String password = "teCompartomiclave";
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hash = encoder.encode(password);
        System.out.println("Hash BCrypt para '" + password + "': " + hash);
    }
}

