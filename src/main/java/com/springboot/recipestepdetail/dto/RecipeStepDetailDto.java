package com.springboot.recipestepdetail.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
public class RecipeStepDetailDto {
    public static class Post {
        @Schema(description = "요리 순서", example = "1")
        private int stepOrder;

        @Schema(description = "요리 설명", example = "밥을 뜨겁게 데워줍니다.")
        @NotBlank(message = "요리 설명은 필수입니다.")
        private String description;

        @Schema(description = "요리 사진 URL", example = "https://image.url/step1.jpg")
        private String image;
    }

    @Getter
    @Builder
    public static class Response {
        private int stepOrder;
        private String description;
        private String image;
    }
}
