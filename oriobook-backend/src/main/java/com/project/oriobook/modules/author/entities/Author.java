package com.project.oriobook.modules.author.entities;

import com.project.oriobook.common.enums.CommonEnum;
import com.project.oriobook.core.entity.base.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "authors")
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Author extends BaseEntity {
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
}
