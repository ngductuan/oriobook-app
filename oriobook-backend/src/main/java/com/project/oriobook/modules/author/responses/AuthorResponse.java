package com.project.oriobook.modules.author.responses;

import com.project.oriobook.core.entity.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class AuthorResponse extends BaseEntity {
    private String id;

    private String name;

    private String image;

    private int publishedBook;
}
