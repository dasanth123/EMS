package com.example.demo.utils;

import jakarta.persistence.Column;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;


@Getter
@Setter
@MappedSuperclass
public class DateAudit {
    @CreationTimestamp
    @Column(nullable = false,updatable = false)
    private Instant createdAt;
    @UpdateTimestamp
    @Column(nullable = false)
    private  Instant updatedAt;

    @PrePersist
    protected  void  onCreate(){
        createdAt = Instant.now();
        updatedAt = Instant.now();
    }
      @PreUpdate
    protected void  onUpdate(){
        updatedAt=Instant.now();
    }



}
