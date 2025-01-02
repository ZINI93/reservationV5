package com.example.reservationV5.controller;

import ch.qos.logback.core.joran.conditional.IfAction;
import com.example.reservationV5.controller.form.ReservationForm;
import com.example.reservationV5.domain.Member;
import com.example.reservationV5.domain.Reservation;
import com.example.reservationV5.domain.ReservationStatus;
import com.example.reservationV5.dto.MemberDto;
import com.example.reservationV5.dto.ReservationDto;
import com.example.reservationV5.service.MemberService;
import com.example.reservationV5.service.ReservationService;
import com.fasterxml.jackson.databind.annotation.JsonValueInstantiator;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.engine.ValidatorImpl;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;
    private final MemberService memberService;


    @GetMapping
    public String reservationMain(HttpSession session, Model model) {
        String loginId = (String) session.getAttribute("loginId");
        Member member = memberService.findByLoginId(loginId);
        model.addAttribute("member", member);
        return "reservations/main";
    }


    /**
     * 예약생성
     */
    @GetMapping("/new")
    public String showCreateReservation(Model model){
        model.addAttribute("reservationForm",new ReservationForm());
        return "reservations/createReservation";
    }


    @PostMapping("/new")
    public String createReservation(@Valid @ModelAttribute ReservationForm reservationForm, BindingResult result, HttpSession session) {
        String loginId = (String)session.getAttribute("loginId");
        Member member = memberService.findByLoginId(loginId);

        ReservationDto reservationDto = ReservationDto.builder()
                .memberId(member.getMemberId())
                .description(reservationForm.getDescription())
                .dateTime(reservationForm.getDateTime())
                .status(ReservationStatus.CONFIRMED)
                .build();

        reservationService.createReservation(reservationDto);
        return "redirect:/reservations";
    }

    /**
     * 예약 list
     */

    @GetMapping("/list")
    public String listReservations(HttpSession session, Model model){
        Long memberId = (Long)session.getAttribute("memberId");
        log.info("controller memberId session 체크 :{}", memberId);
        if (memberId == null){
            return "redirect:/members/login";
        }
        List<Reservation> reservations = reservationService.getReservationByMember(memberId);  //세션에 저전된 로그인 아이디 꺼내온다
        model.addAttribute("reservations", reservations);
        return "reservations/list";

    }






}

