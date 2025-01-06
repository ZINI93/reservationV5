package com.example.reservationV5.domain.reservation.dto;

import com.example.reservationV5.domain.member.entity.Member;
import com.example.reservationV5.domain.reservation.entitiy.Reservation;
import com.example.reservationV5.domain.reservation.entitiy.ReservationStatus;
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

    // memberId만 저장
    private Member member;

    private Long memberId;

    @NotBlank
    private String description;

    @NotNull
    private LocalDateTime dateTime;

    private ReservationStatus status = ReservationStatus.CONFIRMED;

    public ReservationDto() {
    }

    @Builder
    public ReservationDto(Long id,Long memberId, Member member, String description, LocalDateTime dateTime, ReservationStatus status) {
        this.id = id;
        this.memberId = memberId;
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
                .member(member)  // memberId를 이용해 실제 Member 조회 후 넘겨줄 수 있음
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
                .member(reservation.getMember())
                .memberId(reservation.getMember().getMemberId()) // Member의 ID를 memberId로 설정
                .description(reservation.getDescription())
                .dateTime(reservation.getDateTime())
                .status(reservation.getStatus())
                .build();
    }
}