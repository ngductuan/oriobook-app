package com.project.oriobook.modules.category.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.oriobook.core.entity.base.BaseEntity;
import com.project.oriobook.modules.product.entities.Product;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "categories")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category extends BaseEntity {
    @Column(unique = true)
    private String name;

    @Column(name = "is_main")
    private Boolean isMain = false;

    @Column(name = "num_products")
    private int numProducts = 0;

    // for sub (children -> ref parent)
    @ManyToOne
    @JoinColumn(name = "parent_id")
    @JsonBackReference
    // @JsonIgnore
    private Category parentNode;

    // for main (parent -> for children)
    @OneToMany(mappedBy = "parentNode", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonManagedReference
    private List<Category> children = new ArrayList<>();

    // for products ref to category
    @OneToMany(mappedBy = "categoryNode", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Product> products = new ArrayList<>();
}
