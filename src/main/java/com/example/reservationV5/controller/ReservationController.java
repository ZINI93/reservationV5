package com.example.reservationV5.controller;

import com.example.reservationV5.domain.Member;
import com.example.reservationV5.service.MemberService;
import com.example.reservationV5.service.ReservationService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
}
