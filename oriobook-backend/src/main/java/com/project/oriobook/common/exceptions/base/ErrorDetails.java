package com.project.oriobook.common.exceptions.base;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorDetails {
    private String field;
    private String detail;
}
