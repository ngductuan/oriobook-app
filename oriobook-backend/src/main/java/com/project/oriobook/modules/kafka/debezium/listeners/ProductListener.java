package com.project.oriobook.modules.kafka.debezium.listeners;

import com.project.oriobook.common.constants.ElasticIndexConst;
import com.project.oriobook.common.constants.KafkaTopic;
import com.project.oriobook.common.utils.KafkaUtil;
import com.project.oriobook.modules.elastic.services.IElasticService;
import com.project.oriobook.modules.kafka.debezium.messages.ProductMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@KafkaListener(id = "productGroup", topics = {KafkaTopic.DB_PRODUCTS})
public class ProductListener {
    private final IElasticService elasticService;

    @KafkaHandler
    public void listenProduct(Object productObject) throws Exception {
        ProductMessage productMessage = KafkaUtil.convertObjectToMessage(productObject, ProductMessage.class);

        if (productMessage != null){
            System.out.println("Received: " + productMessage);
            elasticService.updateDataToElastic(productMessage, ElasticIndexConst.PRODUCTS);

        } else {
            System.out.print("Kafka message is null");
        }
    }

    @KafkaHandler(isDefault = true)
    public void unknown(Object object) {
        System.out.println("Received unknown: " + object);
    }
}
