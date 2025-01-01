package com.example.reservationV5.controller.form;


import com.example.reservationV5.dto.MemberDto;
import jakarta.validation.constraints.*;
import lombok.Data;

@Data
public class MemberForm {



    @NotBlank(message = "ID를 입력하시오")
    @Size(min = 4, max = 15, message = "4 - 15 이내의 ID를 입력하시오")
    private String loginId;

    @NotBlank(message = "비밀번호를 입력하시오")
    @Size(min = 8, max = 20, message = "8 - 20 이내의 비밀번호를 입력하시오")
    private String password;

    @NotEmpty(message = "이름은 null 이거나 비워둘 수 없습니다.")
    private String userName;

    @NotBlank(message = "전화번호를 입력하시오")
    @Pattern(regexp = "^080-\\d{4}-\\d{4}$", message = "유효한 국내 전화번호를 입력하세요. (형식: 080-XXXX-XXXX)")
    private String phoneNumber;


    public MemberDto toUserDto() {
        return MemberDto.builder()
                .loginId(loginId)
                .password(password)
                .userName(userName)
                .phoneNumber(phoneNumber)
                .build();
    }


}
