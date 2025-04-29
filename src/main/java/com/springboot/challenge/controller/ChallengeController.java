package com.springboot.challenge.controller;

import com.springboot.challenge.dto.ChallengeResponseDto;
import com.springboot.challenge.entity.ChallengeCategory;
import com.springboot.challenge.mapper.ChallengeMapper;
import com.springboot.challenge.service.ChallengeService;
import com.springboot.dto.MultiResponseDto;
import com.springboot.member.dto.MemberChallengeDto;
import com.springboot.member.entity.Member;
import com.springboot.member.entity.MemberChallenge;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.ArrayList;
import java.util.List;

@Tag(name = "도전과제 컨트롤러", description = "도전과제 관련 컨트롤러")
@RestController
@RequestMapping("/challenges")
@Validated
public class ChallengeController {
    private final ChallengeService challengeService;
    private final ChallengeMapper challengeMapper;

    public ChallengeController(ChallengeService challengeService, ChallengeMapper challengeMapper) {
        this.challengeService = challengeService;
        this.challengeMapper = challengeMapper;
    }

    @Operation(summary = "내가 달성한 도전과제만 조회", description = "도전과제 전체 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "도전과제 전체 목록 조회 완료"),
            @ApiResponse(responseCode = "400", description = "Challenge Validation failed")
    })
    @GetMapping("my-challenges")
    public ResponseEntity getMyChallenges(@Parameter(hidden = true) @AuthenticationPrincipal Member member,
                                          @RequestParam(defaultValue = "50")@Positive int size,
                                          @RequestParam(defaultValue = "1") @Positive int page) {

        Page<MemberChallenge> pageChallengeCategories = challengeService.findAllChallenges(page - 1, size, member.getMemberId());
        List<MemberChallenge> challengeCategories = pageChallengeCategories.getContent();

        List<ChallengeResponseDto> responseDtos = challengeMapper.challengesToChallengeResponseDtos(challengeCategories);

        return new ResponseEntity(new MultiResponseDto<>(responseDtos, pageChallengeCategories), HttpStatus.OK);
    }

    @Operation(summary = "도전과제 전체 목록 조회", description = "도전과제 전체 목록을 조회합니다.")    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "도전과제 전체 목록 조회 완료"),
            @ApiResponse(responseCode = "400", description = "Challenge Validation failed")
    })
    @GetMapping
    public ResponseEntity getChallenges(@Parameter(hidden = true) @AuthenticationPrincipal Member member,
                                        @RequestParam(defaultValue = "50")@Positive int size,
                                        @RequestParam(defaultValue = "1") @Positive int page) {
        Page<MemberChallenge> pageChallengeCategories = challengeService.findAllChallenges(page - 1, size, member.getMemberId());
        List<MemberChallenge> challengeCategories = pageChallengeCategories.getContent();

        List<ChallengeResponseDto> responseDtos = challengeMapper.challengesToChallengeResponseDtos(challengeCategories);

        return new ResponseEntity(new MultiResponseDto<>(responseDtos, pageChallengeCategories), HttpStatus.OK);
    }
}