package com.project.oriobook;

import com.project.oriobook.common.enums.CommonEnum;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;

@SpringBootApplication(exclude = {ReactiveSecurityAutoConfiguration.class})
public class OriobookApplication {

    public static void main(String[] args) {
        SpringApplication.run(OriobookApplication.class, args);
    }

}
