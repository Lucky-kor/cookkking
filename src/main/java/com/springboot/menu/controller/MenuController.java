package com.springboot.menu.controller;

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

import javax.validation.Valid;
import java.util.List;

@Tag(name = "메뉴 추천 컨트롤러", description = "재료 기반 메뉴 추천 관련 API")
@RestController
@RequestMapping("/recommendations/menus")
public class MenuController {

    @Operation(summary = "메뉴 추천", description = "사용자가 선택한 재료를 바탕으로 카테고리별 메뉴를 추천합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "추천된 메뉴 조회 완료"),
            @ApiResponse(responseCode = "400", description = "잘못된 재료 선택 (주재료 2개 이상 필수)")
    })
    @PostMapping
    public ResponseEntity recommendMenus( // RequestBody 담아야됨
            @Parameter(hidden = true) @AuthenticationPrincipal Member member) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "메뉴 재추천", description = "추천 버튼을 눌러 다른 메뉴를 재추천 받습니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "재추천된 메뉴 조회 완료")
    })
    @PostMapping("/refresh")
    public ResponseEntity refreshRecommendedMenus( // RequestBody 담아야됨
            @Parameter(hidden = true) @AuthenticationPrincipal Member member) {

        return new ResponseEntity<>(HttpStatus.OK);
    }
}