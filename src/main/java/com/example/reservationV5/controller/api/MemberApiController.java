package com.example.reservationV5.controller.api;

import com.example.reservationV5.domain.member.dto.MemberDto;
import com.example.reservationV5.domain.member.entity.Member;
import com.example.reservationV5.domain.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/members")
@RequiredArgsConstructor
public class MemberApiController {

    private final MemberService memberService;


    //전체 회원조회
    @GetMapping
    public ResponseEntity<List<MemberDto>> allMembers(){
        List<MemberDto> members = memberService.findAllMembers();
        return ResponseEntity.ok(members);
    }

    // 특정 회원 조회
    @GetMapping("/{memberId}")
    public ResponseEntity<MemberDto>findById(@PathVariable Long memberId){
        MemberDto member = memberService.findByMemberId(memberId);
        return ResponseEntity.ok(member);
    }


    // 회원 생성
    @PostMapping
    public ResponseEntity<Long> createMember(@RequestBody MemberDto memberDto){
        Long memberId = memberService.signupMember(memberDto);
        return ResponseEntity.ok(memberId);
    }

    // 회원 업데이트
    @PutMapping("/{memberId}")
    public ResponseEntity<MemberDto> editMember(@PathVariable Long memberId, @RequestBody MemberDto memberDto){
        MemberDto editMember = memberService.updateMember(memberDto);
        return ResponseEntity.ok(editMember);
    }

    //회원 삭제
    @DeleteMapping("/{memberId}")
    public ResponseEntity<Void> deleteMember(@PathVariable Long memberId, MemberDto memberDto) {
        memberService.deleteMember(memberDto);
        return ResponseEntity.noContent().build();
    }
}
