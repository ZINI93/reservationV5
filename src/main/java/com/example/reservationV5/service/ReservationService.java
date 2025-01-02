package com.example.reservationV5.service;

import com.example.reservationV5.domain.Member;
import com.example.reservationV5.domain.Reservation;
import com.example.reservationV5.domain.ReservationStatus;
import com.example.reservationV5.dto.ReservationDto;
import com.example.reservationV5.repository.MemberRepository;
import com.example.reservationV5.repository.ReservationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {

    private final MemberRepository memberRepository;
    private final ReservationRepository reservationRepository;



    // 예약 생성
    @Transactional
    public Long createReservation(ReservationDto reservationDto) {

        log.info("Searching for member with Id: {}", reservationDto.getMemberId());
        Member member = memberRepository.findById(reservationDto.getMemberId())
                .orElseThrow(() -> new EntityNotFoundException("회원이 존재하지 않습니다.."));

        log.info("Searching for member with Id2: {}", reservationDto.getMemberId());

        if (isDuplicateReservation(member, reservationDto.getDateTime())) {
            throw new IllegalArgumentException("이미 해당 시간에 예약이 존재합니다.");
        }

        Reservation reservation = Reservation.builder()
                .member(member)
                .description(reservationDto.getDescription())
                .dateTime(reservationDto.getDateTime())
                .status(ReservationStatus.CONFIRMED)
                .build();

        return reservationRepository.save(reservation).getId();
    }

    //중복 검증
    @Transactional
    private boolean isDuplicateReservation(Member member, LocalDateTime dateTime){
        List<Reservation> reservations = reservationRepository.findByMember(member);
        return reservations.stream()
                .anyMatch(reservation -> reservation.getDateTime().equals(dateTime));
    }


    //특정 loginId로 회원 조회 - list만들기
    public List<Reservation> getReservationByMember(Long memberId){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("해당 회원을 찾을 수 없습니다."));
        log.info("memberId 체크 :{}", memberId);

        return reservationRepository.findByMember(member);
    }






}
