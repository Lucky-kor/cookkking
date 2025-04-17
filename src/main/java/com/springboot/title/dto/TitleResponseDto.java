package com.springboot.title.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class TitleResponseDto {
    private long titleId;

    private String name;

    private String description;
}
