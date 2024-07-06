package com.project.oriobook.modules.category.entities;

import com.project.oriobook.core.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "categories")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category extends BaseEntity {
    private String name;

    @Column(name = "is_main")
    private boolean isMain;

    @Column(name = "num_products")
    private int num_products;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Category parentNode;
}
