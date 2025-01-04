package com.example.reservationV5.service;

import com.example.reservationV5.domain.Member;
import com.example.reservationV5.domain.Reservation;
import com.example.reservationV5.domain.ReservationStatus;
import com.example.reservationV5.dto.ReservationDto;
import com.example.reservationV5.repository.MemberRepository;
import com.example.reservationV5.repository.ReservationRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.User;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {


    @Mock
    private ReservationRepository reservationRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    ReservationService reservationService;

    private ReservationDto reservationDto;


    //crud , 예약시간으로 조회 , 이름으로 조회, 예약시간 업데이트,  USer 삭제 3일전만 가능 admin 가능,

    @BeforeEach
    void setUp() {

    }

    @DisplayName("예약 생성")
    @Test
    void createReservation() {

        //given

        Long memberId = 1L;
        Member member = Member.builder().memberId(1L).build();


        ReservationDto reservationDto = ReservationDto.builder()
                .id(1L)
                .memberId(memberId)
                .description("충치치료")
                .dateTime(LocalDateTime.now().plusDays(2))
                .status(ReservationStatus.CONFIRMED)
                .build();


        when(memberRepository.findById(memberId)).thenReturn(Optional.of(member));
        when(reservationRepository.save(any(Reservation.class))).thenAnswer(invocation -> invocation.getArgument(0));


        //when
//        ReservationDto savedReservation = reservationService.createReservation(reservationDto);
//
//        //then
//        assertNotNull(savedReservation);
//        assertEquals("충치치료", savedReservation.getDescription());
//        verify(reservationRepository, times(1)).save(any(Reservation.class));
//
//    }

    }}