package com.example.reservationV5.domain.member.entity;



public enum UserRole {
    USER("ユーザー"),
    ADMIN("管理者");


    private final String description;

    UserRole(String description) {
        this.description = description;
    }
}
