package com.project.oriobook.modules.product.entities;

import com.project.oriobook.core.entity.BaseEntity;
import com.project.oriobook.modules.category.entities.Category;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "products")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product extends BaseEntity {
    private String name;

    private String image;

    private Float price;

    private String description;

    private int stock;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category categoryNode;
}
