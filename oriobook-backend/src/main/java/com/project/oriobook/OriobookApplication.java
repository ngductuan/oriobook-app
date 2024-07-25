package com.project.oriobook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication() // exclude = {ReactiveSecurityAutoConfiguration.class}
public class OriobookApplication {
    public static void main(String[] args) {
        SpringApplication.run(OriobookApplication.class, args);
    }
}
