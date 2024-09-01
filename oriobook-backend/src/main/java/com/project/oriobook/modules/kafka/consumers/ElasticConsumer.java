package com.project.oriobook.modules.kafka.consumers;

import com.project.oriobook.modules.user.dto.UpdateUserProfileDTO;
import com.project.oriobook.modules.user.entities.User;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class ElasticConsumer {
    @KafkaListener(topics = "oriobook.oriobook.users", groupId = "userGroup")
    public void UserConsumer(UpdateUserProfileDTO dto) {
        System.out.println("Consumed user message: " + dto);
    }
}
