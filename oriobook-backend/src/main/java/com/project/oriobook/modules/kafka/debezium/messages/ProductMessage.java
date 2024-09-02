package com.project.oriobook.modules.kafka.debezium.messages;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.project.oriobook.core.message.base.MessageBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ProductMessage extends MessageBase {
    private String name;
    private String image;
    private double price;
    private String description;
    private int stock;

    @JsonProperty("author_id")
    private String authorId;

    @JsonProperty("category_id")
    private String categoryId;
}
