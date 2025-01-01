package com.example.reservationV5.domain;


import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public class TimeStamp {

    // 생성일시

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    // 수정일시
    @Column(nullable = false)
    @LastModifiedDate
    private LocalDateTime modifiedAt;
}