package com.example.reservationV5.controller;

import com.example.reservationV5.domain.member.dto.MemberDto;
import com.example.reservationV5.domain.member.service.MemberService;
import com.example.reservationV5.domain.reservation.dto.ReservationDto;
import com.example.reservationV5.domain.reservation.entitiy.Reservation;
import com.example.reservationV5.domain.reservation.repository.ReservationRepository;
import com.example.reservationV5.domain.reservation.repository.ReservationRepositoryCustom;
import com.example.reservationV5.domain.reservation.service.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@RequestMapping("/admin")
@RequiredArgsConstructor
@Controller
public class AdminController {

    private final MemberService memberService;
    private final ReservationService reservationService;
    private final ReservationRepository reservationRepository;

    //메인
    @GetMapping
    public String adminMainPage() {
        return "admin/main";
    }

    // 리스트
    @GetMapping("/members-list")
    public String membersList(Model model){

        List<MemberDto> members = memberService.findAllMembers();
        model.addAttribute("members", members);

        return "admin/members-list";
    }

    //회원 수정
    @GetMapping("/members/{memberId}/edit")
    public String editMemberForm(@PathVariable("memberId") Long memberId, Model model) {
        log.info("id check :{}", memberId);
        MemberDto member = memberService.findByMemberId(memberId);
        model.addAttribute("member", member);
        return "admin/editMember";
    }

    @PostMapping("/members/{memberId}/edit")
    public String editMember(@PathVariable Long memberId, Model model, @ModelAttribute MemberDto memberDto) {
        memberDto.setMemberId(memberId);
        memberService.updateMember(memberDto);
        return "redirect:/admin/members-list";
    }

    @GetMapping("/today-reservations")
    public String AllTodayReservationsForm(Model model){
        List<ReservationDto> reservations = reservationService.getTodayReservations();
        model.addAttribute("reservations", reservations);
        return "admin/todayReservations";
    }

    @GetMapping("/reservation-list")
    public String reservationList(@RequestParam(required = false) String name,
                                  @RequestParam(required = false) String phoneNumber,
                                  Model model){
        List<ReservationDto> reservations = reservationService.findReservations(name, phoneNumber);
        model.addAttribute("reservations",reservations);
        return "admin/reservationList";
    }

    @GetMapping("/reservation/{reservationId}/edit")
    public String editReservationFrom(@PathVariable Long reservationId, Model model){
        Reservation reservation = reservationRepository.findById(reservationId).
                orElseThrow(() -> new NoSuchElementException("해당 예약을 찾을 수 없습니다"));
        model.addAttribute("reservation",reservation);

        return "admin/editReservation";
    }

    @PostMapping("/reservation/{reservationId}/edit")
    public String editReservation(@PathVariable Long reservationId, Model model, @ModelAttribute ReservationDto reservationDto){
        reservationDto.setId(reservationId);
        reservationService.updateReservation(reservationDto);
        return "redirect:/admin/reservation-list";
    }

    @PostMapping("/reservation/{reservationId}/cancel-delete")
    public String cancelAndDeleteReservation(@PathVariable Long reservationId) {
        reservationService.cancelAndDeleteReservation(reservationId);
        return "redirect:/admin/reservation-list"; // 취소 및 삭제 후 예약 목록 페이지로 리다이렉트
    }


}

