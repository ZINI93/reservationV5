package com.example.reservationV5.domain.member.dto;


import com.example.reservationV5.domain.member.entity.Member;
import com.example.reservationV5.domain.member.entity.UserRole;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
public class MemberDto {


    private Long memberId;

    @NotBlank(message = "ID를 입력하시오")
    @Size(min = 4,max = 15,message = "4 - 15 이내의 ID를 입력하시오")
    private String username;

    @NotBlank(message = "비밀번호를 입력하시오")
    @Size(min = 8,max = 20,message = "8- 20 이내의 비밀번호를 입력하시오")
    private String password;

    @NotBlank
    private String name;

    @NotBlank(message = "전화번호를 입력하시오")
    @Pattern(regexp = "^080-\\d{4}-\\d{4}$", message = "유효한 국내 전화번호를 입력하세요. (형식: 080-XXXX-XXXX)")
    private String phoneNumber;

    private UserRole role;

    @Builder
    public MemberDto(Long memberId,String username, String password, String name, String phoneNumber, UserRole role) {
        this.memberId = memberId;
        this.username = username ;
        this.password = password;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.role = role;
    }

    public static MemberDto fromEntity(Member member){
        return MemberDto.builder()
                .memberId(member.getMemberId())
                .username(member.getUsername())
                .name(member.getName())
                .phoneNumber(member.getPhoneNumber())
                .role(member.getRole())
                .build();
    }

    /**
     * DTO -> 엔티티 변환
     */
    public Member toEntity(String encodedPassword) {
        return Member.builder()
                .username(this.username)
                .password(encodedPassword) // 암호화된 비밀번호 전달
                .name(this.name)
                .phoneNumber(this.phoneNumber)
                .role(UserRole.ADMIN) // 기본 역할 설정
                .build();
    }

}

