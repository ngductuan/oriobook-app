package com.project.oriobook.core.entity.base;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.oriobook.common.constants.CommonConst;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    protected String id;

    @Column(name = "created_at")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern=CommonConst.DATETIME_FORMAT, timezone=CommonConst.TIME_ZONE)
    protected LocalDateTime createdAt;

    @Column(name = "updated_at")
    @JsonFormat(shape=JsonFormat.Shape.STRING, pattern=CommonConst.DATETIME_FORMAT, timezone=CommonConst.TIME_ZONE)
    protected LocalDateTime updatedAt;

    @PrePersist
    private void onCreate() {
        if(this.id == null) {
            this.id = UUID.randomUUID().toString();
        }
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    private void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
}
