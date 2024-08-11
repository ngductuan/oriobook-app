package com.project.oriobook.modules.author.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.project.oriobook.common.enums.CommonEnum;
import com.project.oriobook.core.entity.base.BaseEntity;
import com.project.oriobook.modules.product.entities.Product;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "authors")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
public class Author extends BaseEntity {
    @Column(unique = true)
    private String name;

    private String image;

    private String description;

    private String style;

    private String address;

    @JoinColumn(name = "date_of_birth")
    private String dateOfBirth;

    @Enumerated(EnumType.STRING)
    private CommonEnum.GenderEnum gender;

    @JoinColumn(name = "published_book")
    private int publishedBook;

    @OneToMany(mappedBy = "authorNode", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Product> products = new ArrayList<>();
}
