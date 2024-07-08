package com.project.oriobook.modules.product.entities;

import com.project.oriobook.core.entity.BaseEntity;
import com.project.oriobook.modules.category.entities.Category;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "products")
// @Data
// @EqualsAndHashCode(callSuper = true)
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity{
    @Id
    private String id;

    private String name;

    private String image;

    private Double price;

    private String description;

    private int stock;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category categoryNode;

//     authorId
}
