package com.project.oriobook.modules.upload_file.entities;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonKey;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

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
