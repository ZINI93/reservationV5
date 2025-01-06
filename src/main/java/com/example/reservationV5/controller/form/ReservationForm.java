package com.example.reservationV5.controller.form;


import com.example.reservationV5.domain.member.entity.Member;
import com.example.reservationV5.domain.reservation.entitiy.ReservationStatus;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ReservationForm {

    private Member member;

    private Long memberId;

    @NotBlank(message = "예약 설명을 입력해주세요.")
    private String description;

    @NotNull(message = "예약 시간을 입력해주세요.")
    @Future(message = "예약 시간은 미래의 날짜여야 합니다.")
    private LocalDateTime dateTime;


    private ReservationStatus status = ReservationStatus.CONFIRMED;


    @Builder
    public ReservationForm(Member member, Long memberId, String description, LocalDateTime dateTime, ReservationStatus status) {
        this.member = member;
        this.memberId = memberId;
        this.description = description;
        this.dateTime = dateTime;
        this.status = status;
    }
}

