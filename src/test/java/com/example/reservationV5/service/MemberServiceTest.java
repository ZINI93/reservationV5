package com.example.reservationV5.service;

import com.example.reservationV5.domain.Member;
import com.example.reservationV5.dto.MemberDto;
import com.example.reservationV5.repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class MemberServiceTest {

    @Mock
    private MemberRepository memberRepository;  // Mock 객체로 MemberRepository를 생성

    @Mock
    private PasswordEncoder passwordEncoder;  //Mock 객체로 PasswordEncoder를 생성

    @InjectMocks
    private MemberService memberService;   // Mock 객체들을 주입받아 실제로 테스트할 MemberService 생성

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);  // 테스트 객체 초기화
    }

    /**
     * 회원가입 테스트
     */

    @Test
    void 회원가입() {
        //given
        MemberDto memberDto = new MemberDto( "testUser", "password123","zini", "010-4912-6363");

        // Member 엔티티를 생성하여 회원가입 후 저장결과를 시뮬레이션
        Member member = Member.builder()
                .id(memberDto.getId())
                .loginId(memberDto.getLoginId())
                .userName(memberDto.getUserName())
                .password("encodedPassword")
                .phoneNumber(memberDto.getPhoneNumber())
                .build();

        //Mock 동작 정의 : passwordEncoder 와 memberRepository의 동작 설정
        when(passwordEncoder.encode(memberDto.getPassword())).thenReturn("encodedPassword");
        when(memberRepository.save(any(Member.class))).thenReturn(member);

        //when  테스트 대상 메서드를 호출
        Long memberId = memberService.signupMember(memberDto);

        //Then 예상 결과와 실제 값의 비교
        assertNotNull(member.getLoginId()); //저장된 회원 ID가 null이 아님을 확인
        verify(passwordEncoder, times(1)).encode(memberDto.getPassword());  //패스워드 인코딩이 호출되었는지 확인
        verify(memberRepository, times(1)).save(any(Member.class)); // save가 되었는지 확인.

    }

}