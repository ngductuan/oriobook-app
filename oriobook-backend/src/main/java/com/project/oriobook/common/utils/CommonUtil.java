package com.project.oriobook.common.utils;

import com.project.oriobook.common.constants.CommonConst;
import com.project.oriobook.common.exceptions.base.JwtExceptionBase;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommonUtil {
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(CommonConst.DATE_DTO_FORMAT);
    private static final MapperUtil mapperUtil = new MapperUtil();

    public static void responseFilterException(Exception e, HttpServletResponse response) {
        try {
            response.setContentType("application/json");
            if (e instanceof JwtExceptionBase jwtE) {
                response.setStatus(jwtE.getStatusCode());
                response.getWriter().write(mapperUtil.convertMapToJson(jwtE.getErrorResponse()));
            } else {
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.getWriter().write(e.getMessage());
            }
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static LocalDate convertStringToDate(String date) {
        return date != null ? LocalDate.parse(date, DATE_FORMATTER) : null;
    }

    public static LocalDateTime convertStringToDateTime(String date) {
        return date != null ? LocalDate.parse(date, DATE_FORMATTER).atStartOfDay() : null;
    }
}