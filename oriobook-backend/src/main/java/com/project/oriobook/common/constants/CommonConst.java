package com.project.oriobook.common.constants;

import java.util.List;

public class CommonConst {
    public static final String SUCCESS_REQUEST = "success";
    public static final String UNKNOWN_ERROR = "unknown error";
    public static final String BAD_REQUEST_EXCEPTION = "Bad Request Exception";
    public static final String DATE_EXAMPLE = "01/01/2023";
    public static final String DATE_EXAMPLE_2 = "01/01/2025";

    // JWT
    public static final String ACCESS = "access"; // mode
    public static final String REFRESH = "refresh"; // mode
    public static final String BEARER_KEY = "Bearer";
    public static final String CLAIM_ID = "id";
    public static final String CLAIM_EMAIL = "email";
    public static final String CLAIM_ROLE = "role";

    // Validation DTO
    public static final String DATE_BOND_MSG = "invalid date format, expected dd/MM/yyyy";

    // Pagination params
    public static final int DEFAULT_PAGE = 0;
    public static final int DEFAULT_LIMIT = 10;

    // Date format
    public static final String DATE_BOND_REGEX = "\\d{2}/\\d{2}/\\d{4}";
    public static final String DATE_DTO_FORMAT = "dd/MM/yyyy";
    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String TIME_ZONE = "UTC";

    // Cloudinary folder
    public static final String CLOUDINARY_PRODUCTS_FOLDER = "products";

    // Allowed image file extensions
    public static final List<String> ALLOWED_IMAGE_EXTENSIONS = List.of("png", "jpg", "jpeg");

    // Redis - exception
    public static final String REDIS_CONVERT_DATA = " Lists must not be null and must have the same size";

    // DTO - exception
}
