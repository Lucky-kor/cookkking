package com.springboot.challenge.controller;

import com.springboot.member.entity.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;

@Tag(name = "도전과제 컨트롤러", description = "도전과제 관련 컨트롤러")
@RestController
@RequestMapping("/challenges")
@Validated
public class ChallengeController {
    @Operation(summary = "카테고리별 도전과제 전체 목록 조회", description = "카테고리별 도전과제 전체 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "카테고리별 도전과제 전체 목록 조회 완료"),
            @ApiResponse(responseCode = "400", description = "Challenge Validation failed")
    })
    @GetMapping("/{category-id}")
    public ResponseEntity getChallengesOfCategory(@PathVariable @Positive long categoryId,
                                                 @AuthenticationPrincipal Member member) {
        return new ResponseEntity(HttpStatus.OK);
    }

    @Operation(summary = "도전과제 단일 조회", description = "도전과제를 단일 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "도전과제 단일 조회 완료"),
            @ApiResponse(responseCode = "400", description = "Challenge Validation failed")
    })
    @GetMapping("/{challenge-id}")
    public ResponseEntity getChallenge(@PathVariable @Positive long challengeId,
                                       @AuthenticationPrincipal Member member) {
        return new ResponseEntity(HttpStatus.OK);
    }
}
