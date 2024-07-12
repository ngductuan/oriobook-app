package com.project.oriobook.modules.upload_file.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CloudinaryEntity {
    // @JsonProperty("public_id")
    private String publicId;

    private String url;

    // @JsonProperty("secure_url")
    private String secureUrl;

    private String format;

    // @JsonProperty("resource_type")
    private String resourceType;

    private String width;

    private String height;
}
