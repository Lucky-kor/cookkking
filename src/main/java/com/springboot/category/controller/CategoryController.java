package com.springboot.category.controller;

import com.springboot.member.entity.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "카테고리 컨트롤러", description = "메뉴, 도전과제, 재료 카테고리 관련 API")
@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Operation(summary = "메뉴 카테고리 전체 조회", description = "모든 메뉴 카테고리를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "메뉴 카테고리 조회 완료")
    })
    @GetMapping("/menus")
    public ResponseEntity getMenuCategories(
            @Parameter(hidden = true) @AuthenticationPrincipal Member member) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "도전과제 카테고리 전체 조회", description = "모든 도전과제 카테고리를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "도전과제 카테고리 조회 완료")
    })
    @GetMapping("/challenges")
    public ResponseEntity getChallengeCategories(
            @Parameter(hidden = true) @AuthenticationPrincipal Member member) {

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "재료 카테고리 전체 조회", description = "모든 재료 카테고리를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "재료 카테고리 조회 완료")
    })
    @GetMapping("/ingredients")
    public ResponseEntity getIngredientCategories(
            @Parameter(hidden = true) @AuthenticationPrincipal Member member) {

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
