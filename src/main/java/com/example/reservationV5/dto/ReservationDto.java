package com.example.reservationV5.dto;

import com.example.reservationV5.domain.Member;
import com.example.reservationV5.domain.Reservation;
import com.example.reservationV5.domain.ReservationStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ReservationDto {

    private Long id;

    private Long MemberId;

    private Member member;

    @NotBlank
    private String description;

    @NotNull
    private LocalDateTime dateTime;


    private ReservationStatus status = ReservationStatus.CONFIRMED;

    public ReservationDto() {
    }
    @Builder
    public ReservationDto(Long id,Long memberId, Member member, String description, LocalDateTime dateTime, ReservationStatus status) {
        MemberId = memberId;
        this.member = member;
        this.description = description;
        this.dateTime = dateTime;
        this.status = status;
    }

    /**
     * DTO -> 엔티티 변환
     */
    public Reservation toEntity(Member member) {
        return Reservation.builder()
                .member(member)
                .description(this.description)
                .dateTime(this.dateTime)
                .status(ReservationStatus.CONFIRMED)
                .build();
    }


    /**
     * 엔티티 -> DTO 변환
     */
    public static ReservationDto fromEntity(Reservation reservation) {
        return ReservationDto.builder()
                .id(reservation.getId())
                .memberId(reservation.getMember().getMemberId())
                .description(reservation.getDescription())
                .dateTime(reservation.getDateTime())
                .status(reservation.getStatus())
                .build();
    }

}