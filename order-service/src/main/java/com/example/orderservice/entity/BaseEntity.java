package com.example.orderservice.entity;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class BaseEntity extends PanacheEntityBase {

    @Column(nullable = false)
    public String createdBy;

    @Column(nullable = false)
    public LocalDateTime createdDate;

    public String updatedBy;

    public LocalDateTime updatedDate;
}
