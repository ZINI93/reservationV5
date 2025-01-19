package com.example.reservationV5.controller;

import com.example.reservationV5.controller.form.ReservationForm;
import com.example.reservationV5.domain.member.dto.MemberDto;
import com.example.reservationV5.domain.member.entity.Member;
import com.example.reservationV5.domain.member.repository.MemberRepository;
import com.example.reservationV5.domain.reservation.entitiy.Reservation;
import com.example.reservationV5.domain.reservation.entitiy.ReservationStatus;
import com.example.reservationV5.domain.reservation.dto.ReservationDto;
import com.example.reservationV5.domain.member.service.MemberService;
import com.example.reservationV5.domain.reservation.service.ReservationService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.bridge.Message;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Slf4j
@Controller
@RequestMapping("/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final MemberRepository memberRepository;
    private final ReservationService reservationService;
    private final MemberService memberService;


//    @GetMapping   // 복잡하게 짤 필요없이 간결하게 짜면된다.  - 오류 원인
//    public String reservationMain(HttpSession session, Model model) {
//        String username = (String) session.getAttribute("username");
//        MemberDto member = memberService.findByUsername(username);
//        model.addAttribute("member", member);
//        return "reservations/main";
//    }


    // 예약 관련 메인페이지
    @GetMapping
    public String mainPage(){
        return "reservations/main";
    }


    /**
     * 예약생성
     */
    @GetMapping("/new")
    public String showCreateReservation(Model model){
        model.addAttribute("ReservationDto",new ReservationDto());
        return "reservations/createReservation";
    }


    @PostMapping("/new")
    public String createReservation(ReservationDto reservationDto, Model model,@AuthenticationPrincipal UserDetails userDetails) {

        String username = userDetails.getUsername();

        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("사용자를 찾을 수 없습니다."));

        try {
            reservationService.saveReservation(reservationDto, member.getMemberId());
            model.addAttribute("message", "예약이 성공적으로 추가되었습니다.");
            return "redirect:/reservations";
        }catch (IllegalArgumentException e){
            model.addAttribute("error",e.getMessage());
            return "redirect:/reservations";
        }



        // 아래는 실패 코드

//            @Valid @ModelAttribute ReservationForm reservationForm,
//            BindingResult result,
//            @AuthenticationPrincipal UserDetails userDetails) {
//
//        // 현재 로그인한 사용자 정보 확인
//        String username = userDetails.getUsername();  //일단 세션은 이렇게 들어와야한다.
//        log.info("Authenticated username: {}", username);
//
//        // 사용자 정보 가져오기
//        MemberDto member = memberService.findByUsername(username);
//        log.info("memberInfo check: {}", member);
//
//
//        // ReservationDto 생성
//        ReservationDto reservationDto = ReservationDto.builder()
//                .memberId(member.getMemberId())
//                .description(reservationForm.getDescription())
//                .dateTime(reservationForm.getDateTime())
//                .status(ReservationStatus.CONFIRMED)
//                .build();
//
//        // 예약 생성 서비스 호출
//        reservationService.createReservation(reservationDto);
//
//        return "redirect:/reservations"; // 성공 시 예약 목록으로 리다이렉트

    }

    /**
     * 예약 list
     */

//    @GetMapping("/list")
//    public String listReservations(HttpSession session, Model model){
//        Long memberId = (Long)session.getAttribute("memberId");
//        log.info("controller memberId session 체크 :{}", memberId);
//        if (memberId == null){
//            return "redirect:/members/login";
//        }
//        List<Reservation> reservations = reservationService.getReservationByMember(memberId);  //세션에 저전된 로그인 아이디 꺼내온다
//        model.addAttribute("reservations", reservations);
//        return "reservations/list";
//
//    }
    @GetMapping("/list")
    public String listReservations(Model model){

        List<ReservationDto> reservations = reservationService.findAllReservations();
        model.addAttribute("reservations",reservations);

        return "reservations/list";

    }






}

