package com.example.reservationV5.service;

import com.example.reservationV5.domain.member.entity.Member;
import com.example.reservationV5.domain.reservation.entitiy.Reservation;
import com.example.reservationV5.domain.reservation.entitiy.ReservationStatus;
import com.example.reservationV5.domain.reservation.dto.ReservationDto;
import com.example.reservationV5.domain.member.repository.MemberRepository;
import com.example.reservationV5.domain.reservation.repository.ReservationRepository;
import com.example.reservationV5.domain.reservation.service.ReservationService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class ReservationServiceTest {


    @Mock
    private MemberRepository memberRepository;

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationService reservationService;

    private Member mockMember;
    private ReservationDto mockReservationDto;



    //crud , 예약시간으로 조회 , 이름으로 조회, 예약시간 업데이트,  USer 삭제 3일전만 가능 admin 가능,

    @BeforeEach
    void setUp() {
        // Mock Member 객체 설정
        mockMember = Member.builder()
                .memberId(1L)
                .username("testUser")
                .password("testPassword")
                .name("Test Name")
                .phoneNumber("01012345678")
                .build();

        // Mock ReservationDto 객체 설정
        mockReservationDto = ReservationDto.builder()
                .memberId(mockMember.getMemberId())
                .description("Test reservation")
                .dateTime(LocalDateTime.now().plusHours(1))  // 예약 시간을 1시간 뒤로 설정
                .status(ReservationStatus.CONFIRMED)
                .build();
    }

//    @DisplayName("예약 생성")
//    @Test
//    void createReservation_Success() {
//        // Member 객체가 DB에서 반환될 때를 Mock
//        when(memberRepository.findById(mockMember.getMemberId())).thenReturn(Optional.of(mockMember));
//
//        // 중복 예약이 없다는 것을 Mock
//        when(reservationRepository.save(any(Reservation.class))).thenReturn(Reservation.builder()
//                .id(1L)
//                .member(mockMember)
//                .description(mockReservationDto.getDescription())
//                .dateTime(mockReservationDto.getDateTime())
//                .status(ReservationStatus.CONFIRMED)
//                .build());
//
//        // 예약 생성 메서드 호출
//        Long reservationId = reservationService.createReservation(mockReservationDto);
//
//        // 검증
//        assertNotNull(reservationId);
//        assertEquals(1L, reservationId);
//        verify(memberRepository, times(1)).findById(mockMember.getMemberId());
//        verify(reservationRepository, times(1)).save(any(Reservation.class));
//    }


}