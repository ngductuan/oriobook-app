package com.project.oriobook.common.configs;

import com.project.oriobook.common.exceptions.CommonException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;

@Configuration
public class KafkaConfig {
    @Bean
    public KafkaListenerErrorHandler consumerErrorHandler() {
        return (message, exception) -> {
            System.err.println("Error processing message: " + message.getPayload());
            System.err.println("Error: " + exception.getMessage());
            try {
                throw new CommonException.GetKafkaData("Global getKafkaData " + exception.getMessage());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }
}
