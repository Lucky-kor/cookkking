package com.springboot.challenge.mapper;

import com.springboot.challenge.dto.ChallengeResponseDto;
import com.springboot.challenge.entity.Challenge;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChallengeMapper {
    List<ChallengeResponseDto> challengesToChallengeResponseDtos(List<Challenge> challenges);
    ChallengeResponseDto challengeToChallengeResponseDto(Challenge challenge);
}
