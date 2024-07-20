package com.project.oriobook.common.constants;

import java.util.List;

public class CommonConst {
    public static final String SUCCESS_REQUEST = "success";
    public static final String UNKNOWN_ERROR = "unknown error";
    public static final String BAD_REQUEST_EXCEPTION = "Bad Request Exception";

    // JWT
    public static final String ACCESS = "access"; // mode
    public static final String REFRESH = "refresh"; // mode
    public static final String BEARER_KEY = "Bearer";

    // Validation DTO
    public static final String DATE_BOND_MSG = "Invalid date format, expected dd/MM/yyyy";

    // Date format
    public static final String DATE_BOND_REGEX = "\\d{2}/\\d{2}/\\d{4}";
    public static final String DATE_DTO_FORMAT = "dd/MM/yyyy";

    // Cloudinary folder
    public static final String CLOUDINARY_PRODUCTS_FOLDER = "products";

    // Allowed image file extensions
    public static final List<String> ALLOWED_IMAGE_EXTENSIONS = List.of("png", "jpg", "jpeg");

    // Redis - exception
    public static final String REDIS_CONVERT_DATA = " Lists must not be null and must have the same size";

    // DTO - exception
    public static final String INVALID_DATE_DTO_FORMAT = " Invalid date DTO format";
}
