package com.project.oriobook;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication() // exclude = {ReactiveSecurityAutoConfiguration.class}
public class OriobookApplication {
    @Value("http://localhost:${server.port}/swagger-ui/index.html")
    private String swaggerPath;

    private static OriobookApplication instance;

    public OriobookApplication() {
        instance = this;
    }

    public static void main(String[] args) {
        SpringApplication.run(OriobookApplication.class, args);
        System.out.println("OriobookApplication is running at: " + instance.swaggerPath);
    }
}
