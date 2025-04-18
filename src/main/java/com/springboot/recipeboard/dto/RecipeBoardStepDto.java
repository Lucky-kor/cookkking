package com.springboot.recipeboard.dto;

import com.springboot.recipestepdetail.dto.RecipeStepDetailDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class RecipeBoardStepDto {
    @Getter
    @Setter
    @NoArgsConstructor
    public static class Post {
        private long recipeStepId;
        private int stepOrder;
        private List<RecipeStepDetailDto.Post> recipeStepDetails;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Patch {
        private long recipeStepId;
        private int stepOrder;
        private List<RecipeStepDetailDto.Patch> recipeStepDetails;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class response {
        private long recipeStepId;
        private int stepOrder;
        private List<RecipeStepDetailDto.Response> recipeStepDetails;
    }
}
