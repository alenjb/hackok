package com.cobin.hackok.domain.member.domain;

public enum MemberGrade {
    BASIC("일반회원"), PREMIUM("우수회원"), VIP("VIP회원");

    final String gradeName;

    MemberGrade(String gradeName){this.gradeName = gradeName;}
}
