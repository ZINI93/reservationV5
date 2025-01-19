package com.example.reservationV5.controller.api;

import com.example.reservationV5.domain.reservation.dto.ReservationDto;
import com.example.reservationV5.domain.reservation.entitiy.Reservation;
import com.example.reservationV5.domain.reservation.repository.ReservationRepository;
import com.example.reservationV5.domain.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.OutputKeys;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
@RequiredArgsConstructor
public class ReservationApiController {

    private final ReservationService reservationService;


    @GetMapping  // 일단 전체조회는 이거로 하고 추후에  findReservations로 개발
    public ResponseEntity<List<ReservationDto>> allReservations() {
        List<ReservationDto> reservations = reservationService.findAllReservations();
        return ResponseEntity.ok(reservations);
    }

    //전체 예약 중 - 전화번호, 이름으로  조회

    @GetMapping("/search")
    public ResponseEntity<List<ReservationDto>> allReservations(
            @RequestParam String name,
            @RequestParam String phoneNumber
    ) {
        List<ReservationDto> reservations = reservationService.findReservations(name, phoneNumber);
        return ResponseEntity.ok(reservations);
    }

//    //에약생성  일단 보류 service에 수정필요할거 같음
//    @PostMapping
//    public ResponseEntity<Long> crateReservation(@RequestBody ReservationDto reservationDto,
//                                                 @RequestParam Long memberId){
//        ReservationDto reservationDto1 = reservationService.saveReservation(reservationDto,memberId);
//
//    }

    // 업데이트
    @PutMapping("/{reservationId}")
    public ResponseEntity<ReservationDto> editReservation(@PathVariable Long reservationId,
                                                          @RequestBody ReservationDto reservationDto){
        ReservationDto editReservation = reservationService.updateReservation(reservationDto);
        return ResponseEntity.ok(editReservation);
    }

    //취소
    @DeleteMapping("/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long reservationId){
        reservationService.cancelAndDeleteReservation(reservationId);
        return ResponseEntity.noContent().build();
    }







}
