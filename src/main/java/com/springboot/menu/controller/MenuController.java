package com.springboot.menu.controller;

import com.springboot.dto.MultiResponseDto;
import com.springboot.dto.SingleResponseDto;
import com.springboot.ingredient.mapper.IngredientMapper;
import com.springboot.member.entity.Member;
import com.springboot.menu.dto.MenuDto;
import com.springboot.menu.entity.Menu;
import com.springboot.menu.mapper.MenuMapper;
import com.springboot.menu.service.MenuService;
import com.springboot.recipeboard.entity.RecipeBoard;
import com.springboot.recipeboard.mapper.RecipeBoardMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Tag(name = "메뉴 컨트롤러", description = "메뉴 관련 컨트롤러")
@RestController
@RequestMapping("/menus")
public class MenuController {
    private final MenuService menuService;
    private final MenuMapper menuMapper;
    private final IngredientMapper ingredientMapper;
    private final RecipeBoardMapper recipeBoardMapper;
    private static final String MENU_URI = "menus/";

    public MenuController(MenuService menuService, MenuMapper menuMapper, IngredientMapper ingredientMapper, RecipeBoardMapper recipeBoardMapper) {
        this.menuService = menuService;
        this.menuMapper = menuMapper;
        this.ingredientMapper = ingredientMapper;
        this.recipeBoardMapper = recipeBoardMapper;
    }

    @Operation(summary = "메뉴 추천 및 재추천", description = "사용자가 선택한 재료를 바탕으로 카테고리별 메뉴를 추천합니다. 매번 랜덤하게 레시피를 추천합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "추천된 메뉴 조회 완료"),
            @ApiResponse(responseCode = "400", description = "잘못된 재료 선택 (주재료 2개 이상 필수)")
    })
    @PostMapping("/recommendations")
    public ResponseEntity recommendMenus( // RequestBody 담아야됨
                                          @Parameter(hidden = true) @AuthenticationPrincipal Member member,
                                          @RequestBody MenuDto.RecommendationsIngredientDto IngredientDto,
                                          @Parameter(description = "페이지, 기본값은 1") @RequestParam(value = "page", defaultValue = "1") int page,
                                          @Parameter(description = "사이즈, 기본값은 10") @RequestParam(value = "size", defaultValue = "10") int size) {

        List<RecipeBoard> recipeBoard = menuService.recommendMenus(ingredientMapper.ingredientsDtoToIngredients(IngredientDto), page - 1, size);
//        List<RecipeBoard> recipeBoardList = recipeBoard.getContent();
        Page<RecipeBoard> pageImpe = new PageImpl<>(
                recipeBoard,                      // content
                PageRequest.of(page - 1, size),     // pageable (현재 page-1 주는 거 맞게)
                recipeBoard.size()                 // totalElements (전체 개수 그냥 리스트 사이즈로)
        );

        return new ResponseEntity(
                new MultiResponseDto<>(recipeBoardMapper.recipeBoardsToRecipeBoardResponseDtos(recipeBoard),
                        pageImpe), HttpStatus.OK);
    }

    @Operation(summary = "메뉴 등록", description = "새로운 메뉴를 등록합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "메뉴 등록 완료"),
            @ApiResponse(responseCode = "400", description = "메뉴 Validation 실패")
    })
    @PostMapping
    public ResponseEntity postMenu(@RequestBody MenuDto.Post menuPostDto,
                                   @Parameter(hidden = true) @AuthenticationPrincipal Member member) {
        // 메뉴 등록 로직
        Menu menu = menuService.createMenu(menuMapper.menuPostDtoToMenu(menuPostDto));
        URI location = URI.create(MENU_URI + menu.getMenuId());

        return ResponseEntity.created(location).build();
    }

    @Operation(summary = "메뉴 수정", description = "기존 메뉴 정보를 수정합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "메뉴 수정 완료"),
            @ApiResponse(responseCode = "400", description = "메뉴 Validation 실패")
    })
    @PatchMapping
    public ResponseEntity patchMenu(@RequestBody MenuDto.Patch menuPatchDto,
                                    @Parameter(hidden = true) @AuthenticationPrincipal Member member) {
        // 메뉴 수정 로직
        Menu menu = menuService.updateMenu(menuMapper.menuPatchDtoToMenu(menuPatchDto));
        return new ResponseEntity<>(new SingleResponseDto<>(menuMapper.menuToMenuResponseDto(menu)), HttpStatus.OK);
    }

    @Operation(summary = "메뉴 단일 조회", description = "특정 메뉴의 상세 정보를 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "메뉴 조회 완료"),
            @ApiResponse(responseCode = "404", description = "메뉴를 찾을 수 없음")
    })
    @GetMapping("/{menu-id}")
    public ResponseEntity getMenu(@PathVariable("menu-id") long menuId,
                                  @Parameter(hidden = true) @AuthenticationPrincipal Member member) {
        // 메뉴 조회 로직
        Menu menu = menuService.findMenu(menuId);
        return new ResponseEntity<>(new SingleResponseDto<>(menuMapper.menuToMenuResponseDto(menu)), HttpStatus.OK);
    }

    @Operation(summary = "메뉴 전체 조회", description = "모든 메뉴를 페이지네이션과 함께 조회합니다.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "메뉴 전체 조회 완료"),
            @ApiResponse(responseCode = "400", description = "요청 파라미터 Validation 실패")
    })
    @GetMapping
    public ResponseEntity getMenus(@Parameter(hidden = true) @AuthenticationPrincipal Member member,
                                   @RequestParam(value = "page", defaultValue = "1") int page,
                                   @RequestParam(value = "size", defaultValue = "10") int size) {
        // 메뉴 목록 조회 로직
        Page<Menu> pageMenus = menuService.findMenus(page - 1, size);
        // Page<Menu> -> List<MenuDto.SimpleResponse>
        List<Menu> menus = pageMenus.getContent();
        // Page<Menu> -> List<MenuDto.SimpleResponse>
        List<MenuDto.Response> menuSimpleResponses = menuMapper.menusToMenuResponseDtos(menus);
        return new ResponseEntity<>(new MultiResponseDto<>(menuSimpleResponses, pageMenus), HttpStatus.OK);

    }

}