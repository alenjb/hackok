package com.cobin.hackok.domain.member.dto;

import com.cobin.hackok.domain.member.domain.MemberGrade;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class Member {
    private Long id;            //아이디
    @NotBlank private String loginId;     //로그인 아이디
    private String name;        //이름
    @NotBlank private String password;    //비밀번호
    private String birthDay;    //생년월일
    private String joinDay;     //가입 날짜
    private MemberGrade memberGrade;   //회원등급
}
