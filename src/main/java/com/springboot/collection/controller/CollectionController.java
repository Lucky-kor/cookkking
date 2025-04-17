package com.springboot.collection.controller;

import com.springboot.collection.dto.CollectionDto;
import com.springboot.member.entity.Member;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Positive;

@Tag(name = "도감 컨트롤러", description = "도감 관련 컨트롤러")
@RestController
@RequestMapping("/collections")
@Validated
public class CollectionController {
    @Operation(summary = "도감 카테고리 생성", description = "도감 카테고리를 생성합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "도감 카테고리 생성 완료"),
            @ApiResponse(responseCode = "400", description = "Collection Validation failed")
    })
    @PostMapping
    public ResponseEntity postCollection(@Valid @RequestBody CollectionDto.Post collectionPostDto,
                                         @Parameter(hidden = true) @AuthenticationPrincipal Member member) {
        // Post Controller 로직 작성 해야함

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "도감 카테고리 수정", description = "도감 카테고리를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "도감 카테고리 수정 완료"),
            @ApiResponse(responseCode = "400", description = "Collection Validation failed")
    })
    @PatchMapping("/{collection-id}")
    public ResponseEntity patchCollection(@PathVariable("collection-id") @Positive long collectionId,
                                          @Valid @RequestBody CollectionDto.Patch collectionPatchDto,
                                          @Parameter(hidden = true) @AuthenticationPrincipal Member member) {
        // Patch Controller 로직 작성 해야함

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "도감 카테고리 전체 조회", description = "도감 카테고리를 전체 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "도감 카테고리 전체 조회 완료"),
            @ApiResponse(responseCode = "400", description = "Collection Validation failed")
    })
    @GetMapping
    public ResponseEntity getCollection(@Parameter(hidden = true) @AuthenticationPrincipal Member member) {
        // 도감 카테고리 전체 조회 로직 작성 해야함

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "도감 카테고리 삭제", description = "도감 카테고리를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "도감 카테고리 삭제 완료"),
            @ApiResponse(responseCode = "400", description = "Collection Validation failed")
    })
    @DeleteMapping("/{collection-id}")
    public ResponseEntity deleteCollection(@PathVariable @Positive long collectionId,
                                           @AuthenticationPrincipal Member member) {
        // Delete Controller 로직 작성 해야함

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "도감 카테고리 내 메뉴 목록 조회", description = "도감 카테고리 내 메뉴 목록을 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "도감 카테고리 내 메뉴 목록 조회 완료"),
            @ApiResponse(responseCode = "400", description = "Collection Validation failed")
    })
    @GetMapping("/{collection-id}/collectionitem")
    public ResponseEntity getCollectionItem(@PathVariable @Positive long collectionId,
                                            @AuthenticationPrincipal Member member) {
        // 도감 카테고리 내 메뉴 목록 조회 Controller 로직 작성 해야함

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "도감 카테고리 메뉴 추가", description = "도감 카테고리 메뉴를 추가합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "도감 카테고리 메뉴 추가 완료"),
            @ApiResponse(responseCode = "400", description = "Collection Validation failed")
    })
    @PostMapping("/{collection-id}/collectionitem")
    public ResponseEntity postCollectionItem(@PathVariable @Positive long collectionId,
                                             @AuthenticationPrincipal Member member) {
        // 도감 카테고리 메뉴 추가 Controller 로직 작성 해야함

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "도감 카테고리 메뉴 삭제", description = "도감 카테고리 메뉴를 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "도감 카테고리 메뉴 삭제 완료"),
            @ApiResponse(responseCode = "400", description = "Collection Validation failed")
    })
    @DeleteMapping("/collectionitems/{collectionitem-id}")
    public ResponseEntity deleteCollectionItem(@PathVariable @Positive long collectionItemId,
                                               @AuthenticationPrincipal Member member) {
        // 도감 카테고리 메뉴 삭제 Controller 로직 작성 해야함

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
