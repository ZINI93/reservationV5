package com.example.reservationV5.service;

import com.example.reservationV5.domain.Member;
import com.example.reservationV5.domain.Reservation;
import com.example.reservationV5.domain.ReservationStatus;
import com.example.reservationV5.repository.MemberRepository;
import com.example.reservationV5.repository.ReservationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ReservationService {

    private final MemberRepository memberRepository;
    private final ReservationRepository reservationRepository;

//    /**
//     * 예약생성
//     */
//    public Long createReservation(ReservationDto reservationDto) {
//
//
//        log.info("Searching for member with Id: {}", reservationDto.getMemberId());
//        //Member 조희
//        Member member = memberRepository.findById(reservationDto.getMemberId())
//                .orElseThrow(() -> new EntityNotFoundException("해당 회원을 찾을 수 없습니다."));
//
//        log.info("Searching for member with Id2: {}", reservationDto.getMemberId());
//
//        //중복 예약 방지 로직
//        if (isDuplicateReservation(member, reservationDto.getDate(), reservationDto.getTime())) {
//            throw new IllegalArgumentException("이미 해당 시간에 예약이 존재합니다.");
//        }
//        /**
//         * Reservation Entitiy 생성
//         */
//        Reservation reservation = Reservation.builder()
//                .member(member)
//                .description(reservationDto.getDescription())
//                .dateTime(reservationDto.getDateTime())
//                .status(ReservationStatus.RESERVED)
//                .build();
//
//        return reservationRepository.save(reservation).getId();
//
//    }

    /**
     * 특정 회원의 예약 조회
     */
    @Transactional(readOnly = true)
    public List<Reservation> getReservationsByMember(Long memberid) {
        Member member = memberRepository.findById(memberid)
                .orElseThrow(() -> new EntityNotFoundException("해당 회원을 조회할수 없습니다."));
        return reservationRepository.findByMember(member);
    }

    /**
     * 예약 취소
     */
    public void cancelReservation(Long reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException("해당 예약을 찾을 수 없습니다."));

        //예약 상태를 CANCEL
        reservation.changeStatus(ReservationStatus.CANCEL);
    }

    /**
     * 특정 예약 조회
     */
    public Reservation getReservationById(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new EntityNotFoundException("해당 예약을 찾을 수 없습니다."));
    }

//    /**
//     * 중복 예약 확인
//     */
//    @Transactional(readOnly = true)
//    private boolean isDuplicateReservation(Member member, LocalDate date, LocalTime time) {
//        List<Reservation> reservations = reservationRepository.findByMember(member);
//        return reservations.stream()
//                .anyMatch(reservation -> reservation.get().equals(date) && reservation.getTime().equals(time));
//    }
}
