package com.project.oriobook.common.converters;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.project.oriobook.common.constants.CommonConst;
import com.project.oriobook.common.exceptions.CommonException;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@JsonComponent
public class DateDTODeserializer extends JsonDeserializer<LocalDate> {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(CommonConst.DATE_DTO_FORMAT);

    @Override
    public LocalDate deserialize(JsonParser source, DeserializationContext context)
            throws IOException {
        String date = source.getText();
        String field = source.getParsingContext().getCurrentName();
        try {
            return LocalDate.parse(date, DATE_FORMATTER);
        } catch (Exception e) {
            // throw new IOException("Invalid date format, expected format: " + CommonConst.DATE_DTO_FORMAT, e);
            throw new CommonException.InvalidDateDTO(field + CommonConst.INVALID_DATE_DTO_FORMAT);
        }
    }
}


