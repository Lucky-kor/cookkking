package com.springboot.member.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MyPageResponseDto {
    private String profileImage;
    private String nickName;
    private String title;
    private int point;
}
