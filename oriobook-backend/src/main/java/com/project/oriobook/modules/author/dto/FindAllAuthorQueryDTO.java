package com.project.oriobook.modules.author.dto;

import com.project.oriobook.common.enums.CommonEnum;
import com.project.oriobook.core.pagination.base.QueryFilterBase;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class FindAllAuthorQueryDTO extends QueryFilterBase {
    private String authorName;

    private CommonEnum.GenderEnum gender;

    private Integer publishedBook;
}
