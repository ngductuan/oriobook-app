package com.project.oriobook.common.exceptions.base;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LogicExceptionBase extends Exception{
    private List<ErrorDetails> errorDetails;
    private int statusCode;
    private String message;

    public LogicExceptionBase(int statusCode, String message, List<ErrorDetails> errorDetails) {
        super(message);
        this.errorDetails = errorDetails;
        this.statusCode = statusCode;
        this.message = message;
    }
}
