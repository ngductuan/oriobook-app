package com.project.oriobook.core.response.base;

import org.springframework.beans.factory.annotation.Value;

import java.time.LocalDateTime;

public interface BaseResponse {
    String getId();

    LocalDateTime getCreatedAt();

    LocalDateTime getUpdatedAt();
}
