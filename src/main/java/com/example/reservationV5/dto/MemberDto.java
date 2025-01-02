package com.example.reservationV5.dto;


import com.example.reservationV5.domain.Member;
import com.example.reservationV5.domain.Role;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class MemberDto {


    private Long userId;

    @NotBlank(message = "ID를 입력하시오")
    @Size(min = 4,max = 15,message = "4 - 15 이내의 ID를 입력하시오")
    private String loginId;

    @NotBlank(message = "비밀번호를 입력하시오")
    @Size(min = 8,max = 20,message = "8- 20 이내의 비밀번호를 입력하시오")
    private String password;

    @NotBlank
    private String userName;

    @NotBlank(message = "전화번호를 입력하시오")
    @Pattern(regexp = "^080-\\d{4}-\\d{4}$", message = "유효한 국내 전화번호를 입력하세요. (형식: 080-XXXX-XXXX)")
    private String phoneNumber;

    @Builder
    public MemberDto(String loginId, String password, String userName, String phoneNumber) {
        this.loginId = loginId ;
        this.password = password;
        this.userName = userName;
        this.phoneNumber = phoneNumber;
    }


    /**
     * DTO -> 엔티티 변환
     */
    public Member toEntity(String encodedPassword) {
        return Member.builder()
                .loginId(this.loginId)
                .password(encodedPassword) // 암호화된 비밀번호 전달
                .userName(this.userName)
                .phoneNumber(this.phoneNumber)
                .role(Role.USER) // 기본 역할 설정
                .build();
    }

}

