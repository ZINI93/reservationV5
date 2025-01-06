package com.example.reservationV5.domain.member.service;

import com.example.reservationV5.domain.member.dto.CustomUserDetails;
import com.example.reservationV5.domain.member.entity.Member;
import com.example.reservationV5.domain.member.entity.UserRole;
import com.example.reservationV5.domain.member.dto.MemberDto;
import com.example.reservationV5.domain.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.webauthn.management.UserCredentialRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Slf4j
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    /**
     *  회원등록
     */
    @Transactional
    public Long signupMember(MemberDto memberDto){

        if (memberRepository.existsByUsername(memberDto.getUsername())){
            throw new IllegalArgumentException("이미 존재하는 아이디 입니다.");
        }


        log.info("회원 가입 시작: {}", memberDto);

        Member member = Member.builder()
                .username(memberDto.getUsername())
                .password(passwordEncoder.encode(memberDto.getPassword()))
                .name(memberDto.getName())
                .phoneNumber(memberDto.getPhoneNumber())
                .role(UserRole.USER)
                .build();

        return memberRepository.save(member).getMemberId();

    }


    //유저 로그인
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

//        Member member = memberRepository.findByUsername(username); // 로그인 아이디로 조회
//        if (member == null) {
//            throw new UsernameNotFoundException("User not found");
//
//        }

        Member member = memberRepository.findByUsername(username).orElseThrow();

        return User.builder()
                .username(member.getUsername())
                .password(member.getPassword())
                .roles(member.getRole().toString())
                .build();
    }


//        Member member = memberRepository.findByUsername(username); // 로그인 아이디로 조회
//        if (member == null) {
//            throw new UsernameNotFoundException("User not found");
//        }
//        return new CustomUserDetails(member);  // CustomUserDetails는 UserDetails 구현체





    /**
     * 회원 조회 (id)  삭ㅈ ㅔ 검토..
     */
    public Member findMemberById(Long userId){
        return memberRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을수 없습니다."));
    }

    /**
     * 회원 조회 (userId)
     */

    public MemberDto findByUsername(String username){

        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("유저가 존재하지 않습니다."));

        MemberDto dto = MemberDto.builder()
                .username(member.getUsername())
                .name(member.getName())
                .role(member.getRole())
                .build();

        return dto;
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
    public boolean authenticate(String username, String rawPassword){
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("회원정보를 찾을 수 없습니다."));

        return passwordEncoder.matches(rawPassword,member.getPassword());
    }


}