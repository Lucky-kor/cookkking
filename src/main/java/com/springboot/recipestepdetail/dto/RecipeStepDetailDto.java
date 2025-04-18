package com.springboot.recipestepdetail.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
public class RecipeStepDetailDto {
    @Getter
    @Setter
    @NoArgsConstructor
    public static class Post {
        @Schema(description = "요리 순서", example = "1")
        private int stepOrder;

        @Schema(description = "요리 설명", example = "밥을 뜨겁게 데워줍니다.")
        @NotBlank(message = "요리 설명은 필수입니다.")
        private String description;

        @Schema(description = "요리 사진 URL", example = "https://image.url/step1.jpg")
        private String image;
    }

    @Setter
    @Getter
    @NoArgsConstructor
    public static class Patch {
        private long recipeStepDetailId;

        @Schema(description = "요리 순서", example = "1")
        private int stepOrder;

        @Schema(description = "요리 설명", example = "밥을 뜨겁게 데워줍니다.")
        private String description;

        @Schema(description = "요리 사진 URL", example = "https://image.url/step1.jpg")
        private String image;
    }

    @Getter
    @Builder
    public static class Response {
        private long recipeStepDetailId;
        private int stepOrder;
        private String description;
        private String image;
    }
}
