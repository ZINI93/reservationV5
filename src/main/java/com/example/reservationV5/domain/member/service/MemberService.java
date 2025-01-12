package com.example.reservationV5.domain.member.service;

import com.example.reservationV5.domain.member.entity.Member;
import com.example.reservationV5.domain.member.entity.UserRole;
import com.example.reservationV5.domain.member.dto.MemberDto;
import com.example.reservationV5.domain.member.repository.MemberRepository;
import jakarta.persistence.Id;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.stream.events.EndDocument;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;


@Slf4j
@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    /**
     * 회원등록
     */
    @Transactional
    public Long signupMember(MemberDto memberDto) {

        if (memberRepository.existsByUsername(memberDto.getUsername())) {
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
     * 전체 회원조회 - 관리자 용
     */

    public List<MemberDto> findAllMembers() {
        List<Member> members = memberRepository.findAll();

        if (members.isEmpty()) {
            throw new IllegalStateException("회원 정보가 없습니다.");
        }

        return members.stream()
                .map(MemberDto::fromEntity)
                .collect(Collectors.toList());
    }

    /**
     * 회원 id 조회
     */

    public MemberDto findByMemberId(Long id){
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("회원이 존재하지 않습니다."));

        // MemberDto에 다른 필드들도 설정하여 반환
        return MemberDto.builder()
                .memberId(member.getMemberId())
                .username(member.getUsername())
                .name(member.getName())
                .phoneNumber(member.getPhoneNumber())
                .build();
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
     * name으로 회원조회
     */

    public Member findByName(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("이름은 비워둘 수 없습니다.");
        }
        return memberRepository.findByName(name)
                .orElseThrow(() -> new NoSuchElementException("이름이 존재하지 않습니다."));
    }

    /**
     * phoneNumber로 회원조회
     */
    public Member findByPhoneNumber(String phoneNumber) {
        if (phoneNumber == null || phoneNumber.isBlank()) {
            throw new IllegalArgumentException("폰 넘버는 비워둘수 없습니다.");
        }
        return memberRepository.findByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new NoSuchElementException("phoneNumber가 존재하지 않습니다."));

    }

    /**
     * 회원수정   - name  password, phoneNumber
     */
    @Transactional
    public MemberDto updateMember(MemberDto memberDto){
        //회원조회
        Member member = memberRepository.findById(memberDto.getMemberId())
                .orElseThrow(() -> new NoSuchElementException("회원이 존재하지 않습니다."));

        //비밀번호 인코딩
        String encodedPassword = passwordEncoder.encode(memberDto.getPassword());

        member.update(memberDto.getName(),encodedPassword,memberDto.getPhoneNumber());

        Member savedMember = memberRepository.save(member);  //변경감지 제거체크
        return MemberDto.fromEntity(savedMember);
    }

    /**
     *  회원삭제
     */
    @Transactional
    public void deleteMember(MemberDto memberDto){
        Member member = memberRepository.findById(memberDto.getMemberId())
                .orElseThrow(() -> new NoSuchElementException("회원이 존재하지 않습니다."));

        //존재하면 삭제
        memberRepository.deleteById(memberDto.getMemberId());
    }



//    /**
//     * 회원 조회 (id)  삭ㅈ ㅔ 검토..
//     */
//    public Member findMemberById(Long userId){
//        return memberRepository.findById(userId)
//                .orElseThrow(() -> new IllegalArgumentException("회원 정보를 찾을수 없습니다."));
//    }



//    /**
//     * 회원 삭제
//     */
//    @Transactional
//    public void deleteMember(Long userId){
//        Member member = findMemberById(userId);
//        memberRepository.delete(member);
//    }


    /**
     * 로그인 시 비밀번호 검증
     */
    public boolean authenticate(String username, String rawPassword){
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("회원정보를 찾을 수 없습니다."));

        return passwordEncoder.matches(rawPassword,member.getPassword());
    }


}