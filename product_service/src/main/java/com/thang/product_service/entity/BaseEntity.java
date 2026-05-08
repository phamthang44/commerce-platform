package com.thang.product_service.entity;

import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    private UUID id;

    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    private String createdBy;
    private String updatedBy;

    @PrePersist
    public void prePersistAudit() {
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;

        this.id = UUID.randomUUID();
    }

    @PreUpdate
    public void preUpdateAudit() {
        this.updatedAt = Instant.now();
    }
}
