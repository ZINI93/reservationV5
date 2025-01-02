package com.example.reservationV5.controller.form;


import com.example.reservationV5.domain.ReservationStatus;
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

    private Long MemberId;

    @NotBlank(message = "예약 설명을 입력해주세요.")
    private String description;

    @NotNull(message = "예약 시간을 입력해주세요.")
    @Future(message = "예약 시간은 미래의 날짜여야 합니다.")
    private LocalDateTime dateTime;


    private ReservationStatus status = ReservationStatus.CONFIRMED;


    @Builder
    public ReservationForm(Long memberId, String description, LocalDateTime dateTime, ReservationStatus status) {
        MemberId = memberId;
        this.description = description;
        this.dateTime = dateTime;
        this.status = status;
    }
}
