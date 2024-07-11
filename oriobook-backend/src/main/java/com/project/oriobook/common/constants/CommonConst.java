package com.project.oriobook.common.constants;

import java.util.List;

public class CommonConst {
    public static final String SUCCESS_REQUEST = "success";
    public static final String UNKNOWN_ERROR = "unknown error";
    public static final String BAD_REQUEST_EXCEPTION = "Bad Request Exception";

    // Date format
    public static final String DATETIME_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";
    public static final String DATE_FORMAT = "yyyy-MM-dd";
    public static final String TIME_ZONE = "GMT";

    // Cloudinary folder
    public static final String CLOUDINARY_PRODUCTS_FOLDER = "products";

    // Allowed image file extensions
    public static final List<String> ALLOWED_IMAGE_EXTENSIONS = List.of("png", "jpg", "jpeg");

    // JWT mode
    public static final String ACCESS = "access";
    public static final String REFRESH = "refresh";


}
