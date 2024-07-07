package com.project.oriobook.common.exceptions;

import com.project.oriobook.common.constants.CommonConst;
import com.project.oriobook.common.exceptions.base.BusinessExceptionBase;
import com.project.oriobook.common.exceptions.base.ErrorDetails;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpMessage;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Data
public class ValidationException extends BusinessExceptionBase {
    public ValidationException(BindingResult result) {
        // Convert external constructor and add to super right after
        super(400, CommonConst.BAD_REQUEST_EXCEPTION, convertErrors(result));
    }

    // Initialize when trigger super function. Ensure that super in first row
    private static Map<String, List<String>> convertErrors(BindingResult result) {
        return result.getFieldErrors()
                .stream()
                .collect(Collectors.groupingBy(
                        FieldError::getField,
                        Collectors.mapping(
                                fieldError -> Objects.requireNonNull(fieldError.getDefaultMessage(), CommonConst.UNKNOWN_ERROR), // Chuyển đổi FieldError thành thông báo lỗi
                                Collectors.toList()
                        )
                ));
    }
}
