package com.project.oriobook.modules.author.responses;

import com.project.oriobook.common.enums.CommonEnum;
import com.project.oriobook.core.entity.base.BaseEntity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
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
