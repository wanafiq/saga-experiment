package com.example.inventoryservice.entity;

import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import java.time.LocalDateTime;

public class AuditingEntityListener {

    @PrePersist
    public void onPrePersist(BaseEntity entity) {
        entity.createdBy = "system";
        entity.createdDate = LocalDateTime.now();
    }

    @PreUpdate
    public void onPreUpdate(BaseEntity entity) {
        entity.updatedBy = "system";
        entity.updatedDate = LocalDateTime.now();
    }
}
