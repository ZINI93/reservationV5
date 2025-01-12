package com.example.reservationV5.domain.reservation.entitiy;

import com.example.reservationV5.domain.TimeStamp;
import com.example.reservationV5.domain.member.entity.Member;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@ToString(exclude = "member")
public class Reservation extends TimeStamp {
    @Id @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @Column(nullable = false)
    private String description;   // 내용 selectbox로 구현


    @Column(nullable = false,unique = true)  //15 분단위로 예약가능
    private LocalDateTime dateTime;

    @Enumerated(EnumType.STRING)
    private ReservationStatus status;


    //상태 변경 메서드
    public void changeStatus(ReservationStatus status){
        this.status = status;
    }

    @Builder
    public Reservation(Long id, Member member, String description, LocalDateTime dateTime, ReservationStatus status) {
        this.id = id;
        this.member = member;
        this.description = description;
        this.dateTime = dateTime;
        this.status = status;
    }


    public void validateDateTime(){
        if (dateTime.isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("예약은 과거일 수 없습니다.");
        }
    }

    public void updateReservation(LocalDateTime dateTime, ReservationStatus status){
        this.dateTime = dateTime;
        this.status = status;
    }
}
