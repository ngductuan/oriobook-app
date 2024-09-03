package com.project.oriobook.modules.kafka.debezium.listeners;

import com.project.oriobook.common.constants.ElasticIndexConst;
import com.project.oriobook.common.constants.KafkaConst;
import com.project.oriobook.common.utils.KafkaUtil;
import com.project.oriobook.modules.author.entities.Author;
import com.project.oriobook.modules.author.services.AuthorService;
import com.project.oriobook.modules.category.entities.Category;
import com.project.oriobook.modules.category.services.CategoryService;
import com.project.oriobook.modules.elastic.services.IElasticService;
import com.project.oriobook.modules.kafka.debezium.messages.ProductMessage;
import com.project.oriobook.modules.product.entities.Product;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.KafkaNull;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
@KafkaListener(groupId = KafkaConst.DB_PRODUCTS_GROUP_ID, topics = {KafkaConst.DB_PRODUCTS_TOPIC})
public class ProductListener {
    private final IElasticService elasticService;
    private final CategoryService categoryService;
    private final AuthorService authorService;

    private final ModelMapper modelMapper;

    @KafkaHandler
    public void listenProduct(Object productObject) throws Exception {
        if(productObject == null || productObject == KafkaNull.INSTANCE) {
            System.out.println("Product object is null");
            return;
        }

        try {
            ProductMessage productMessage = KafkaUtil.convertObjectToDMLMessage(productObject, ProductMessage.class);

            if (productMessage != null) {

                // Convert the Kafka message to an Elasticsearch object
                modelMapper.typeMap(ProductMessage.class, Product.class).addMappings(mapper -> {
                    mapper.map(ProductMessage::getCategoryId, Product::setCategoryNode);
                    mapper.map(ProductMessage::getAuthorId, Product::setAuthorNode);
                });
                Product product = modelMapper.map(productMessage, Product.class);

                if(!Objects.equals(productMessage.getOperation(), KafkaConst.DELETE_OP)) {
                    Category category = categoryService.getCategoryById(productMessage.getCategoryId());
                    Author author = authorService.getAuthorById(productMessage.getAuthorId());

                    product.setCategoryNode(category);
                    product.setAuthorNode(author);
                }

                System.out.println("Received: " + productMessage);

                switch (productMessage.getOperation()) {
                    case KafkaConst.INSERT_OP:
                        elasticService.insertDataToElastic(product, ElasticIndexConst.PRODUCTS);
                        break;
                    case KafkaConst.UPDATE_OP:
                        elasticService.updateDataToElastic(product, ElasticIndexConst.PRODUCTS);
                        break;
                    case KafkaConst.DELETE_OP:
                        elasticService.deleteDataFromElastic(product, ElasticIndexConst.PRODUCTS);
                        break;
                    default:
                        System.out.println("Unknown operation: " + productMessage.getOperation());
                }
            } else {
                System.out.println("Delete Kafka message");
            }
        } catch (Exception e) {
            System.err.println("Error processing Kafka message: " + e.getMessage());
            // Optionally rethrow the exception if you want it to propagate.
        }
    }

    @KafkaHandler(isDefault = true)
    public void unknown(Object object) {
        System.out.println("Received unknown: " + object);
    }
}
