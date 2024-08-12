package com.project.oriobook.common.converters;

import org.modelmapper.Converter;
import org.modelmapper.spi.MappingContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeConverter implements Converter<String, LocalDateTime> {
    @Override
    public LocalDateTime convert(MappingContext<String, LocalDateTime> context) {
        return LocalDateTime.parse(context.getSource(), DateTimeFormatter.ISO_DATE_TIME);
    }
}
