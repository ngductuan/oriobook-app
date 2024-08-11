package com.project.oriobook.modules.upload_file.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CloudinaryEntity {
    private String publicId;

    private String url;

    private String secureUrl;

    private String format;

    private String resourceType;

    private String width;

    private String height;
}
