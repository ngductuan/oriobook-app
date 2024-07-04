package com.project.oriobook.modules.product.entity;

import com.project.oriobook.core.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;

@Entity
@Table(name = "products")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product extends BaseEntity {
    @NotEmpty(message = "Name is required")
    private String name;
    @NotEmpty(message = "Image is required")
    private String image;
    @NotEmpty(message = "Price is required")
    private Float price;
    private String description;
    @PositiveOrZero(message = "Stock must be positive")
    private int stock;
    // @ManyToOne
    // @JoinColumn(name = "author_id")
    // private String authorId;
    // @ManyToOne
    // @JoinColumn(name = "category_id")
    // private String categoryId;
}
