package com.example.reservationV5.dto;

import com.example.reservationV5.domain.Member;
import com.example.reservationV5.domain.ReservationStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReservationDto {

    private Long MemberId;

    private Member member;

    @NotBlank
    private String description;

    @NotNull
    private LocalDateTime dateTime;


    private ReservationStatus status = ReservationStatus.CONFIRMED;

    public ReservationDto() {
    }

    public ReservationDto(Long memberId, Member member, String description, LocalDateTime dateTime, ReservationStatus status) {
        MemberId = memberId;
        this.member = member;
        this.description = description;
        this.dateTime = dateTime;
        this.status = status;
    }
}