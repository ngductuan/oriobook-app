package com.project.oriobook.modules.product.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.project.oriobook.core.entity.base.BaseEntity;
import com.project.oriobook.modules.author.entities.Author;
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
public class Product extends BaseEntity{
    private String name;

    private String image;

    private Double price;

    private String description;

    private int stock;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonBackReference
    private Category categoryNode;

    @ManyToOne
    @JoinColumn(name = "author_id")
    @JsonBackReference
    private Author authorNode;
}
