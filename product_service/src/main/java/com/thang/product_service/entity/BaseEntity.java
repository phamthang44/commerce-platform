package com.thang.product_service.entity;

import com.thang.product_service.utils.TsidUtils;
import jakarta.persistence.Id;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {

    @Id
    private Long id;

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

        this.id = TsidUtils.nextId();
    }

    @PreUpdate
    public void preUpdateAudit() {
        this.updatedAt = Instant.now();
    }
}
