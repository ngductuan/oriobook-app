package com.project.oriobook.common.converters;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.project.oriobook.common.constants.CommonConst;
import org.springframework.boot.jackson.JsonComponent;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@JsonComponent
public class DateTimeDTODeserializer extends JsonDeserializer<LocalDateTime> {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(CommonConst.DATE_DTO_FORMAT);

    @Override
    public LocalDateTime deserialize(JsonParser source, DeserializationContext context)
            throws IOException {
        String date = source.getText();
        try {
            LocalDate localDate = LocalDate.parse(date, DATE_FORMATTER);
            return localDate.atStartOfDay();
        } catch (DateTimeParseException e) {
            throw new IOException("Invalid date format, expected format: " + CommonConst.DATE_DTO_FORMAT, e);
        }
    }
}


