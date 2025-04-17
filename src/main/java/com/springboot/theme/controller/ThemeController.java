package com.springboot.theme.controller;

import com.springboot.member.entity.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Positive;
import java.util.List;

@Tag(name = "테마 컨트롤러", description = "도감 테마 관련 API")
@RestController
@RequestMapping("/themes")
public class ThemeController {

    @Operation(summary = "전체 테마 목록 조회", description = "보유 여부에 따라 전체 테마 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "테마 목록 조회 완료")
    })
    @GetMapping
    public ResponseEntity getThemes(
            @Parameter(hidden = true) @AuthenticationPrincipal Member member) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "테마 구매", description = "포인트를 사용하여 테마를 구매합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "테마 구매 성공"),
            @ApiResponse(responseCode = "400", description = "포인트 부족 또는 구매 불가")
    })
    @PostMapping("/{theme-id}/purchase")
    public ResponseEntity purchaseTheme(@PathVariable("theme-id") @Positive long themeId,
                                        @Parameter(hidden = true) @AuthenticationPrincipal Member member) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "테마 장착", description = "보유 중인 테마를 도감에 적용합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "테마 장착 완료"),
            @ApiResponse(responseCode = "403", description = "보유하지 않은 테마")
    })
    @PatchMapping("/{theme-id}/equip")
    public ResponseEntity equipTheme(@PathVariable("theme-id") @Positive long themeId,
                                     @Parameter(hidden = true) @AuthenticationPrincipal Member member) {

        return new ResponseEntity<>(HttpStatus.OK);
    }
}