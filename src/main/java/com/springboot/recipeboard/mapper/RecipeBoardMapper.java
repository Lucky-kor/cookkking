package com.springboot.recipeboard.mapper;

import com.springboot.ingredient.dto.IngredientDto;
import com.springboot.ingredient.entity.Ingredient;
import com.springboot.ingredient.entity.MainIngredient;
import com.springboot.ingredient.entity.SeasoningIngredient;
import com.springboot.menu.entity.Menu;
import com.springboot.recipeboard.dto.RecipeBoardDto;
import com.springboot.recipeboard.dto.RecipeBoardStepDto;
import com.springboot.recipeboard.entity.RecipeBoard;
import com.springboot.recipeboard.entity.RecipeBoardIngredient;
import com.springboot.recipeboard.entity.RecipeBoardStep;
import com.springboot.recipestep.entity.RecipeStep;
import com.springboot.recipestepdetail.dto.RecipeStepDetailDto;
import com.springboot.recipestepdetail.entity.RecipeStepDetail;
import com.springboot.member.entity.Member;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE
)
public interface RecipeBoardMapper {

    // 🔹 핵심 매핑 진입점
    default RecipeBoard recipeBoardPostDtoToRecipeBoard(RecipeBoardDto.Post postDto, Long memberId) {
        if (postDto == null) return null;

        // 메뉴도 선택해야 함.
        RecipeBoard recipeBoard = new RecipeBoard();
        recipeBoard.setTitle(postDto.getTitle());
        recipeBoard.setMenuName(postDto.getMenuName());
        recipeBoard.setContent(postDto.getContent());
        recipeBoard.setImage(postDto.getImage());
        recipeBoard.setRecipeTime(postDto.getRecipeTime());
        recipeBoard.setRecipeStatus(postDto.getRecipeStatus());

        // 메뉴 설정
        Menu menu = new Menu();
        menu.setMenuId(postDto.getMenuId());
        recipeBoard.setMenu(menu);

        // 작성자 설정
        Member member = new Member();
        member.setMemberId(memberId);
        recipeBoard.setMember(member);

        // 단계 리스트 매핑
        List<RecipeBoardStep> boardSteps = postDto.getRecipeBoardSteps().stream()
                .map(stepDto -> recipeBoardStepPostDtoToEntity(stepDto, recipeBoard))
                .collect(Collectors.toList());

        recipeBoard.setRecipeBoardSteps(boardSteps);

        // 재료 리스트 매핑
        List<RecipeBoardIngredient> recipeBoardIngredients = postDto.getIngredients().stream()
                .map(ingredientDto -> RecipeBoardIngredientDtoToEntity(ingredientDto, recipeBoard))
                .collect(Collectors.toList());

        return recipeBoard;
    }

    // 🔹 개별 RecipeBoardIngredient 매핑 (부모 RecipeBoard 주입)
    default RecipeBoardIngredient RecipeBoardIngredientDtoToEntity(IngredientDto.IdRequest dto, RecipeBoard parentBoard) {
        RecipeBoardIngredient recipeBoardIngredient = new RecipeBoardIngredient();

        // ingredient (마스터) ID 참조만
        Ingredient ingredient = dto.getType().equals("SEASONING") ?
                new SeasoningIngredient() :
                new MainIngredient();

        ingredient.setIngredientId(dto.getIngredientId());
        recipeBoardIngredient.setIngredient(ingredient);

        // 양방향 설정
        recipeBoardIngredient.setRecipeBoard(parentBoard);

        return recipeBoardIngredient;
    }

    // 🔹 개별 RecipeBoardStep 매핑 (부모 RecipeBoard 주입)
    default RecipeBoardStep recipeBoardStepPostDtoToEntity(RecipeBoardStepDto.Post dto, RecipeBoard parentBoard) {
        RecipeBoardStep step = new RecipeBoardStep();
        step.setStepOrder(dto.getStepOrder());

        // recipeStep (마스터) ID 참조만
        RecipeStep recipeStep = new RecipeStep();
        recipeStep.setRecipeStepId(dto.getRecipeStepId());
        step.setRecipeStep(recipeStep);

        // 양방향 설정
        step.setRecipeBoard(parentBoard);

        // stepDetail 매핑 및 양방향 설정
        List<RecipeStepDetail> details = dto.getRecipeStepDetails().stream()
                .map(this::recipeStepDetailPostDtoToRecipeStepDetail)
                .collect(Collectors.toList());

        step.setRecipeStepDetails(details); // 내부에서 양방향 설정됨

        return step;
    }

    // 🔹 RecipeStepDetailMapper 메서드 재사용
    default RecipeStepDetail recipeStepDetailPostDtoToRecipeStepDetail(RecipeStepDetailDto.Post dto) {
        RecipeStepDetail stepDetail = new RecipeStepDetail();
        stepDetail.setDetailOrderNumber(dto.getStepOrder());
        stepDetail.setDescription(dto.getDescription());
        stepDetail.setImage(dto.getImage());
        return stepDetail;
    }

    // 아래는 수정해야 하는 메서드
    RecipeBoardDto.Response recipeBoardToRecipeBoardResponseDto(RecipeBoard recipeBoard);
}
