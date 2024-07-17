package com.project.oriobook.common.configs;

import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Configuration
public class JackSonConfig {
    @Value("${spring.jackson.date-format}")
    private String dateFormat;

    @Value("${spring.jackson.time-zone}")
    private String timeZone;

    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {

        return builder -> {
            // Define ZoneId
            ZoneId zoneId = ZoneId.of(timeZone);

            // formatter
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd").withZone(zoneId);
            DateTimeFormatter dateTimeFormatter =  DateTimeFormatter.ofPattern(dateFormat).withZone(zoneId);

            // deserializers
            builder.deserializers(new LocalDateDeserializer(dateFormatter));
            builder.deserializers(new LocalDateTimeDeserializer(dateTimeFormatter));

            // serializers
            builder.serializers(new LocalDateSerializer(dateFormatter));
            builder.serializers(new LocalDateTimeSerializer(dateTimeFormatter));

            // Set default time zone for ObjectMapper
            builder.timeZone(timeZone);
        };
    }
}
