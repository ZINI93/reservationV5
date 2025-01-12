package com.example.reservationV5.domain.reservation.service;

import com.example.reservationV5.domain.member.dto.MemberDto;
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
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReservationService {

    private final MemberRepository memberRepository;
    private final ReservationRepository reservationRepository;


    // 예약 생성

    @Transactional
    public void saveReservation(ReservationDto dto, Long memberId) {
        //회원 조회
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new EntityNotFoundException("회원이 존재하지 않습니다."));

        if (dto.getDateTime().isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("예약 날짜는 현재 시간보다 이전일 수 없습니다.");
        }
        Reservation reservation = Reservation.builder()
                .member(member)
                .description(dto.getDescription())
                .dateTime(dto.getDateTime())
                .status(ReservationStatus.CONFIRMED)
                .build();

        reservationRepository.save(reservation);
    }

    //중복 검증
    @Transactional
    public boolean isDuplicateReservation(Member member, LocalDateTime dateTime) {
        List<Reservation> reservations = reservationRepository.findByMember(member);
        return reservations.stream()
                .anyMatch(reservation -> reservation.getDateTime().equals(dateTime));
    }


    //당일 예약 조회
    public List<ReservationDto> getTodayReservations() {
        List<Reservation> reservations = reservationRepository.findReservationsForToday();
        return reservations.stream()
                .map(ReservationDto::fromEntity)
                .collect(Collectors.toList());
    }

    // 전체 리스트  - 검색기능 name, phoneNumber
    public List<ReservationDto> findReservations(String name, String phoneNumber){
        //querydsl 를 통한 조회
        List<Reservation> reservations = reservationRepository.findReservations(name, phoneNumber);
        return reservations.stream()
                .map(ReservationDto::fromEntity)
                .collect(Collectors.toList());
    }

    //예약 시간
    @Transactional
    public ReservationDto updateReservation(ReservationDto reservationDto){
        Reservation reservation = reservationRepository.findById(reservationDto.getId())
                .orElseThrow(() -> new IllegalArgumentException("예약을 확인할 수 없습니다."));

        reservation.updateReservation(reservationDto.getDateTime(),reservationDto.getStatus());

        return ReservationDto.fromEntity(reservation);
    }


    //취소

    @Transactional
    public void deleteReservation(ReservationDto reservationDto){
        Reservation reservation = reservationRepository.findById(reservationDto.getId())
                .orElseThrow(() -> new NoSuchElementException("예약이 존재하지 않습니다"));

        reservationRepository.delete(reservation);
    }






    //휴대번호로 예약 조회


    //이름으로 조회










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
