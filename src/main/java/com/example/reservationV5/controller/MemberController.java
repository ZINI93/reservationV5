package com.example.reservationV5.controller;

import com.example.reservationV5.domain.Member;
import com.example.reservationV5.dto.MemberDto;
import com.example.reservationV5.service.MemberService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Slf4j
@RequiredArgsConstructor
@RequestMapping("/members")
@Controller
public class MemberController {

    private final MemberService memberService;

    //회원등록
    @GetMapping("/new")
    public String showSignUpForm(Model model) {
        model.addAttribute("memberDto", new MemberDto("","","",""));
        return "members/signup"; // 템플릿 파일 이름
    }


    @PostMapping("/new")
    public String signupMember(@Valid @ModelAttribute MemberDto memberDto, BindingResult result){

        if (result.hasErrors()) {
            log.error("회원가입 오류: {}", result.getAllErrors());
            return "members/signup";
        }
        Long memberId = memberService.signupMember(memberDto);
        log.info("회원가입 성공: ID = {}", memberId);
        return "redirect:/members/login";

    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "members/login";
    }


    @PostMapping("/login")
    public String login(@RequestParam String loginId,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes redirectAttributes) {
        boolean isAuthenticated = memberService.authenticate(loginId, password);
        if (isAuthenticated) {
            // 로그인 성공 시 회원 정보 조회
            Member member = memberService.findByLoginId(loginId);
            // 세션에 사용자 ID와 회원 ID 저장
            session.setAttribute("loginId", loginId);
            session.setAttribute("memberId", member.getUserId()); // 추가
            // 메인 페이지로 리다이렉트
            return "redirect:/reservations";
        } else {
            // 로그인 실패 시 에러 메시지를 포함하여 로그인 페이지로 리다이렉트
            redirectAttributes.addFlashAttribute("error", "로그인 실패: 아이디 또는 비밀번호가 잘못되었습니다.");
            return "redirect:/members/login";
        }
    }
}
