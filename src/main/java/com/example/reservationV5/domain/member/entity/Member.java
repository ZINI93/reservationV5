package com.example.reservationV5.domain.member.entity;

import com.example.reservationV5.domain.TimeStamp;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends TimeStamp {


    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "member_id")
    private Long memberId;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false,unique = true)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Builder
    public Member(Long memberId, String username, String password, String name, String phoneNumber, UserRole role) {
        this.memberId = memberId;
        this.username = username;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    //업데이트
    public void update(String name, String password, String phoneNumber){
        this.name = name;
        this.password =password;
        this.phoneNumber = phoneNumber;

    }
}
