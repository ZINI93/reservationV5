package com.example.reservationV5.service;

import com.example.reservationV5.domain.member.entity.Member;
import com.example.reservationV5.domain.member.service.MemberService;
import com.example.reservationV5.domain.member.dto.MemberDto;
import com.example.reservationV5.domain.member.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
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

//    @Test
//    void 회원가입() {
//        //given
//        MemberDto memberDto = new MemberDto( "testUser", "password123","zini", "010-4912-6363");
//
//        // Member 엔티티를 생성하여 회원가입 후 저장결과를 시뮬레이션
//        Member member = Member.builder()
//                .memberId(memberDto.getMemberId())
//                .username(memberDto.getUsername())
//                .name(memberDto.getName())
//                .password("encodedPassword")
//                .phoneNumber(memberDto.getPhoneNumber())
//                .build();
//
//        //Mock 동작 정의 : passwordEncoder 와 memberRepository의 동작 설정
//        when(passwordEncoder.encode(memberDto.getPassword())).thenReturn("encodedPassword");
//        when(memberRepository.save(any(Member.class))).thenReturn(member);
//
//        //when  테스트 대상 메서드를 호출
//        Long memberId = memberService.signupMember(memberDto);
//
//        //Then 예상 결과와 실제 값의 비교
//        assertNotNull(member.getUsername()); //저장된 회원 ID가 null이 아님을 확인
//        verify(passwordEncoder, times(1)).encode(memberDto.getPassword());  //패스워드 인코딩이 호출되었는지 확인
//        verify(memberRepository, times(1)).save(any(Member.class)); // save가 되었는지 확인.
//
//    }


    @Test
    @DisplayName("전체 회원 조회")
    void findAll(){
        //given
        Member member1 = Member.builder().memberId(1L).username("qwas").name("zini").phoneNumber("080-1122-1123").build();
        Member member2 = Member.builder().memberId(2L).username("qwasa").name("zinizi").phoneNumber("080-1232-1123").build();

        when(memberRepository.findAll()).thenReturn( Arrays.asList(member1,member2));
        //when
        List<MemberDto> result = memberService.findAllMembers();

        //then
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getUsername()).isEqualTo("qwas");
        assertThat(result.get(1).getName()).isEqualTo("zinizi");

        verify(memberRepository, times(1)).findAll();

    }
    @Test
    @DisplayName("ID로 회원 단건 조회 ")
    void findByName(){
        //when
        Member member = Member.builder().memberId(1L).username("qwas").name("zini").phoneNumber("080-1122-1123").build();
        when(memberRepository.findByName("zini")).thenReturn(Optional.of(member));

        //when
        Member result = memberService.findByName("zini");

        //then
        assertNotNull(result);
        assertEquals("zini",result.getName());
    }
    @Test
    @DisplayName("ID로 회원 단건 조회 ")
    void findByPhoneNumber(){
        //when
        Member member = Member.builder().memberId(1L).username("qwas").name("zini").phoneNumber("080-1122-1123").build();
        when(memberRepository.findByPhoneNumber("080-1122-1123")).thenReturn(Optional.of(member));

        //when
        Member result = memberService.findByPhoneNumber("080-1122-1123");

        //then
        assertNotNull(result);
        assertEquals("080-1122-1123",result.getPhoneNumber());
    }

    @Test
    @DisplayName("회원 업데이트")
    void updateMember(){
        //given
        String password = "12341234";
        String encodedPassword = passwordEncoder.encode(password);

        Member member = Member.builder().memberId(1L).name("zini").password(encodedPassword).phoneNumber("080-1122-1123").build();
        MemberDto memberDto = MemberDto.builder().memberId(1L).name("parkJInhee").password(password).phoneNumber("010-1235-1235").build();


        when(passwordEncoder.encode(anyString())).thenReturn(encodedPassword); // 비밀번호 인코딩 mock
        when(memberRepository.findById(1L)).thenReturn(Optional.of(member));
        when(memberRepository.save(any(Member.class))).thenReturn(member);

        //when
        MemberDto updatedMember = memberService.updateMember(memberDto);

        //then
        assertNotNull(updatedMember);
        assertEquals("parkJInhee", updatedMember.getName());
        assertEquals(encodedPassword, updatedMember.getPassword()); // encodedPassword를 비교
        assertEquals("010-1235-1235", updatedMember.getPhoneNumber());
        verify(memberRepository).save(any(Member.class));

    }



}
