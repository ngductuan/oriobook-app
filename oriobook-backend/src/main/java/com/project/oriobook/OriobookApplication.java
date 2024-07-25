package com.project.oriobook;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication() // exclude = {ReactiveSecurityAutoConfiguration.class}
public class OriobookApplication {

    public static void main(String[] args) {
        SpringApplication.run(OriobookApplication.class, args);
    }
}
