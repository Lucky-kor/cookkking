package com.springboot.challenge.dto;

import com.springboot.challenge.entity.Challenge;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ChallengeResponseDto {
    private Long challengeId;

    private String name;

    private String description;

    private int progress; // 0~100 (%)

    private Challenge.ChallengeStatus challengeStatus; // 달성, 미달성

    private String rewardTitle; // 칭호 이름

    private int difficultyLevel; // 쉬움 : 1, 보통 : 2, 어려움 : 3

    private LocalDateTime completedAt; // null이면 미완료
}
