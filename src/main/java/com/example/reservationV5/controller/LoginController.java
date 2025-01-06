package com.example.reservationV5.controller;


import com.example.reservationV5.domain.member.entity.Member;
import com.example.reservationV5.domain.member.service.MemberService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Slf4j
@RequiredArgsConstructor
@Controller
public class LoginController {


    private final MemberService memberService;


    @GetMapping("/login")
    public String loginPage() {
        return "members/login";
    }



    //security에 postMapping따위는 필요없다...

//    @PostMapping("/login")
//    public String login(@RequestParam String username,
//                        @RequestParam String password,
//                        HttpSession session,
//                        RedirectAttributes redirectAttributes) {
//        boolean isAuthenticated = memberService.authenticate(username, password);
//        if (isAuthenticated) {
//            // 로그인 성공 시 회원 정보 조회
//            Member member = memberService.findByusername(username);
//            // 세션에 사용자 ID와 회원 ID 저장
//            session.setAttribute("username", username);
//            session.setAttribute("memberId", member.getMemberId()); // 추가
//
//            // 메인 페이지로 리다이렉트
//            return "redirect:/reservations";
//        } else {
//            // 로그인 실패 시 에러 메시지를 포함하여 로그인 페이지로 리다이렉트
//            redirectAttributes.addFlashAttribute("error", "로그인 실패: 아이디 또는 비밀번호가 잘못되었습니다.");
//            return "redirect:/members/login";
//        }
//
//    }
}
