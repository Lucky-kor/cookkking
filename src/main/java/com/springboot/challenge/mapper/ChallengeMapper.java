package com.springboot.challenge.mapper;

import com.springboot.challenge.dto.ChallengeResponseDto;
import com.springboot.challenge.entity.ChallengeCategory;
import com.springboot.member.entity.MemberChallenge;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ChallengeMapper {
    List<ChallengeResponseDto> challengesToChallengeResponseDtos(List<MemberChallenge> challengeCategories);
    default ChallengeResponseDto challengeToChallengeResponseDto(MemberChallenge challengeCategory) {
        ChallengeResponseDto challengeResponseDto = new ChallengeResponseDto();
        challengeResponseDto.setChallengeId(challengeCategory.getChallengeCategory().getChallengeCategoryid());
        challengeResponseDto.setName(challengeCategory.getChallengeCategory().getName());
        challengeResponseDto.setCategory(challengeCategory.getChallengeCategory().getCategory());
//        challengeResponseDto.setImagePath(challengeCategory.getChallengeCategory().getImagePath());
        challengeResponseDto.setCurrentLevel(challengeCategory.getCurrentLevel());
        challengeResponseDto.setCurrentCount(challengeCategory.getCurrentCount());
        challengeCategory.getChallengeCategory()
                .getChallengeLevels().stream()
                .filter(level -> level.getLevel() == challengeCategory.getCurrentLevel())
                .findFirst()
                .ifPresent(level -> {
                    challengeResponseDto.setGoalCount(level.getGoalCount());
                    challengeResponseDto.setDescription(level.getDescription());
                    challengeResponseDto.setImagePath(level.getImagePath());
                });

        return challengeResponseDto;
    }
}