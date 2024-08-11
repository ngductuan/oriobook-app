package com.project.oriobook.modules.product.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.oriobook.core.entity.base.BaseEntity;
import com.project.oriobook.modules.author.entities.Author;
import com.project.oriobook.modules.category.entities.Category;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "products")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Product extends BaseEntity{
    private String name;

    private String image;

    private Double price;

    private String description;

    private int stock;

    @ManyToOne
    @JoinColumn(name = "category_id")
    @JsonManagedReference
    private Category categoryNode;

    @ManyToOne
    @JoinColumn(name = "author_id")
    @JsonManagedReference
    private Author authorNode;
}
