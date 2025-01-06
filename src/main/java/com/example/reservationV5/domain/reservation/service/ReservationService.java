package com.example.reservationV5.domain.reservation.service;

import com.example.reservationV5.domain.member.entity.Member;
import com.example.reservationV5.domain.reservation.entitiy.Reservation;
import com.example.reservationV5.domain.reservation.entitiy.ReservationStatus;
import com.example.reservationV5.domain.reservation.dto.ReservationDto;
import com.example.reservationV5.domain.member.repository.MemberRepository;
import com.example.reservationV5.domain.reservation.repository.ReservationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PutMapping;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {

    private final MemberRepository memberRepository;
    private final ReservationRepository reservationRepository;



    // 예약 생성

    @Transactional
    public void saveReservation(ReservationDto dto,Long memberId) {
        //회원 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("회원이 존재하지 않습니다."));

        if (dto.getDateTime().isBefore(LocalDateTime.now())){
            throw new IllegalArgumentException("예약 날짜는 현재 시간보다 이전일 수 없습니다.");
        }
        Reservation reservation = Reservation.builder()
                .member(member)
                .description(dto.getDescription())
                .dateTime(dto.getDateTime())
                .status(ReservationStatus.CONFIRMED)
                .build();

        reservationRepository.save(reservation);


//        log.info("Searching for member with Id: {}", dto.getMember().getMemberId());
//
//        Member member = memberRepository.findById(dto.getMember().getMemberId())
//                .orElseThrow(() -> new EntityNotFoundException("회원이 존재하지 않습니다.."));
//
//
//        if (isDuplicateReservation(member, dto.getDateTime())) {
//            throw new IllegalArgumentException("이미 해당 시간에 예약이 존재합니다.");
//        }
//        Reservation reservation = Reservation.builder()
//                .member(member)
//                .description(dto.getDescription())
//                .dateTime(dto.getDateTime())
//                .status(ReservationStatus.CONFIRMED)
//                .build();
//
//        reservationRepository.save(reservation).getId();
//        log.info("회원가입 성공: {}", reservation.getId());
//
//
    }

    //중복 검증
    @Transactional
    private boolean isDuplicateReservation(Member member, LocalDateTime dateTime){
        List<Reservation> reservations = reservationRepository.findByMember(member);
        return reservations.stream()
                .anyMatch(reservation -> reservation.getDateTime().equals(dateTime));
    }


    //특정 loginId로 회원 조회 - list만들기
//    public List<Reservation> getReservationByMember(Long memberId){
//        Member member = memberRepository.findById(memberId)
//                .orElseThrow(() -> new EntityNotFoundException("해당 회원을 찾을 수 없습니다."));
//        log.info("memberId 체크 :{}", memberId);
//
//        return reservationRepository.findByMember(member);
//    }
    public List<Reservation> getAllReservation(){
        return reservationRepository.findAll();
    }






}
