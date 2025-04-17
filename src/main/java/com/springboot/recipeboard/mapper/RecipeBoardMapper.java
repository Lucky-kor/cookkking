package com.springboot.recipeboard.mapper;

import com.springboot.recipeboard.dto.RecipeBoardDto;
import com.springboot.recipeboard.entity.RecipeBoard;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface RecipeBoardMapper {
    RecipeBoard recipeBoardPostDtoToRecipeBoard(RecipeBoardDto.Post recipeBoardPostDto);
    RecipeBoard recipeBoardPatchDtoToRecipeBoard(RecipeBoardDto.Patch recipeBoardPatchDto);
    List<RecipeBoardDto.Response> recipeBoardsToRecipeBoardResponseDtos(List<RecipeBoard> recipeBoards);
    RecipeBoardDto.Response recipeBoardToRecipeBoardResponseDto(RecipeBoard recipeBoard);
}
