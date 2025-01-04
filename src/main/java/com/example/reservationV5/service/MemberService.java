package com.example.reservationV5.service;

import com.example.reservationV5.domain.Member;
import com.example.reservationV5.domain.Role;
import com.example.reservationV5.dto.MemberDto;
import com.example.reservationV5.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    /**
     *  회원등록
     */
    @Transactional
    public Long signupMember(MemberDto memberDto){
        log.info("회원 가입 시작: {}", memberDto);

        Member member = Member.builder()
                .loginId(memberDto.getLoginId())
                .password(passwordEncoder.encode(memberDto.getPassword()))
                .userName(memberDto.getUserName())
                .phoneNumber(memberDto.getPhoneNumber())
                .role(Role.USER)
                .build();

        return memberRepository.save(member).getMemberId();
    }



    /**
     * 회원 조회 (id)  삭ㅈ ㅔ 검토..
     * */
    public Member findMemberById(Long userId){
        return memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을수 없습니다."));
    }

    /**
     * 회원 조회 (userId)
     */
    public Member findByLoginId(String loginId) {
        return memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을 수 없습니다."));
    }
    /**
     * 회원 삭제
     */
    @Transactional
    public void deleteMember(Long userId){
        Member member = findMemberById(userId);
        memberRepository.delete(member);
    }

    /**
     * 로그인 시 비밀번호 검증
     */
    public boolean authenticate(String loginId, String rawPassword){
        Member member = memberRepository.findByLoginId(loginId)
                .orElseThrow(() -> new IllegalArgumentException("회원정보를 찾을 수 없습니다."));

        return passwordEncoder.matches(rawPassword,member.getPassword());
    }

}
