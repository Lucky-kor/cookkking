package com.springboot.recipeboard.controller;

import com.springboot.member.entity.Member;
import com.springboot.recipeboard.dto.RecipeBoardDto;
import com.springboot.recipeboard.entity.RecipeBoard;
import com.springboot.recipeboard.mapper.RecipeBoardMapper;
import com.springboot.recipeboard.service.RecipeBoardService;
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

@Tag(name = "레시피 게시판 컨트롤러", description = "레시피 게시글 관련 컨트롤러")
@RestController
@RequestMapping("/recipes")
@Validated
public class RecipeBoardController {
    private final RecipeBoardService recipeBoardService;
    private final RecipeBoardMapper mapper;

    public RecipeBoardController(RecipeBoardService recipeBoardService, RecipeBoardMapper mapper) {
        this.recipeBoardService = recipeBoardService;
        this.mapper = mapper;
    }

    @Operation(summary = "레시피 게시글 등록", description = "레시피 게시글 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "레시피 게시글 등록 완료"),
            @ApiResponse(responseCode = "400", description = "RecipeBoard Validation failed")
    })
    @PostMapping
    public ResponseEntity postRecipeBoard(@Valid @RequestBody RecipeBoardDto.Post recipeBoardPostDto,
                                          @Parameter(hidden = true) @AuthenticationPrincipal Member member) {
        // Post Controller 로직 작성 해야함
        RecipeBoard recipeBoard = mapper.recipeBoardPostDtoToRecipeBoard(recipeBoardPostDto);

        recipeBoardService.createRecipeBoard(recipeBoard, member.getMemberId());

        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @Operation(summary = "레시피 게시글 수정", description = "레시피 게시글을 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "레시피 게시글 수정 완료"),
            @ApiResponse(responseCode = "400", description = "RecipeBoard Not Found")
    })
    @PatchMapping("/{recipe-id}")
    public ResponseEntity patchRecipeBoard(@PathVariable("recipe-id") @Positive long recipeId,
                                           @Valid @RequestBody RecipeBoardDto.Patch recipeBoardPatchDto,
                                           @Parameter(hidden = true) @AuthenticationPrincipal Member member) {
        // Patch Controller 로직 작성 해야함

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "레시피 게시글 단일 조회", description = "레시피 게시글을 단일 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "레시피 게시글 단일   조회 완료"),
            @ApiResponse(responseCode = "400", description = "RecipeBoard Validation failed")
    })
    @GetMapping("/{recipe-id}")
    public ResponseEntity getRecipeBoard(@PathVariable("recipe-id") @Positive long recipeId,
                                         @Parameter(hidden = true) @AuthenticationPrincipal Member member) {
        // Get Controller 로직 작성 해야함

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "레시피 게시글 삭제", description = "레시피 게시글을 삭제합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "레시피 게시글 삭제 완료"),
            @ApiResponse(responseCode = "400", description = "RecipeBoard Validation failed")
    })
    @DeleteMapping("/{recipe-id}")
    public ResponseEntity deleteRecipeBoard(@PathVariable("recipe-id") @Positive long recipeId,
                                            @Parameter(hidden = true) @AuthenticationPrincipal Member member) {
        // Delete Controller 로직 작성 해야함

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "카테고리별 레시피 게시글 전체 조회", description = "카테고리별 레시피 게시글을 전체 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "카테고리별 레시피 게시글 전체 조회 완료"),
            @ApiResponse(responseCode = "400", description = "RecipeBoard Validation failed")
    })
    @GetMapping("/{category-id}")
    public ResponseEntity categoryGetRecipeBoards(@PathVariable("category-id") @Positive long categoryId,
                                                 @Positive @RequestParam int page,
                                                 @Positive @RequestParam int size,
                                                 @Parameter(hidden = true) @AuthenticationPrincipal Member member) {
        // 카테고리별 레시피 게시글 전체 조회 Controller 로직 작성 해야함

        return new ResponseEntity(HttpStatus.OK);
    }

    @Operation(summary = "메뉴별 레시피 게시글 전체 조회", description = "메뉴별 레시피 게시글을 전체 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "메뉴별 레시피 게시글 전체 조회 완료"),
            @ApiResponse(responseCode = "400", description = "RecipeBoard Validation failed")
    })
    @GetMapping("/{menu-id}")
    public ResponseEntity menuGetRecipeBoards(@PathVariable("menu-id") @Positive long menuId,
                                                  @Positive @RequestParam int page,
                                                  @Positive @RequestParam int size,
                                                  @Parameter(hidden = true) @AuthenticationPrincipal Member member) {
        // 메뉴별 레시피 게시글 전체 조회 Controller 로직 작성 해야함

        return new ResponseEntity(HttpStatus.OK);
    }

    @Operation(summary = "레시피 게시글 북마크 추가/해제", description = "레시피 게시글 북마크를 추가/해제 합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "북마크 추가/해제 완료"),
            @ApiResponse(responseCode = "400", description = "RecipeBoard Not Found")
    })
    @PostMapping("/{recipe-id}/bookmark")
    public ResponseEntity bookmarkRecipeBoard(@PathVariable("recipe-id") @Positive long recipeId,
                                              @Parameter(hidden = true) @AuthenticationPrincipal Member member) {
        // Bookmark Controller 로직 작성 해야함

        return new ResponseEntity(HttpStatus.OK);
    }
}
