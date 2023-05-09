package com.ingemark.springdemo.model;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.ZonedDateTime;

@Getter
@ToString
@SuperBuilder(setterPrefix = "with")
@NoArgsConstructor
@MappedSuperclass
public abstract class BaseAuditEntity {

    @CreationTimestamp
    @Column(updatable = false)
    protected ZonedDateTime createdAt;

    @UpdateTimestamp
    @Column(insertable = false)
    protected ZonedDateTime modifiedAt;

}
