package com.springboot.recipeboard.dto;

import com.springboot.recipeboard.entity.RecipeBoard;
import com.springboot.recipestepdetail.dto.RecipeStepDetailDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

public class RecipeBoardDto {
    @Getter
    public static class Post {
        @Schema(description = "레시피 게시글 제목", example = "존나 맛있는 공기밥 레시피")
        @NotBlank(message = "제목은 공백이 아니어야 합니다.")
        @Size(min = 1, max = 20, message = "제목은 1자 이상 20자 이내여야 합니다.")
        @Pattern(
                regexp = "^(?!\\s)(?!.*\\s{2,}).*$",
                message = "제목은 공백으로 시작하거나 연속된 공백이 포함될 수 없습니다."
        )
        private String title;

        @Schema(description = "메뉴 이름", example = "공기밥")
        @NotBlank(message = "메뉴 이름은 공백이 아니어야 합니다.")
        @Size(min = 1, max = 20, message = "메뉴 이름은 1자 이상 20자 이내여야 합니다.")
        @Pattern(
                regexp = "^(?!\\s)(?!.*\\s{2,}).*$",
                message = "메뉴이름은 공백으로 시작하거나 연속된 공백이 포함될 수 없습니다."
        )
        private String menuName;

        @Schema(description = "레시피 게시글 사진", example = "imageURL")
        @NotBlank(message = "대표 사진은 필수입니다.")
        private String image;

        @Schema(description = "레시피 상태", example = "RECIPE_PUBLIC")
        private RecipeBoard.RecipeStatus recipeStatus = RecipeBoard.RecipeStatus.RECIPE_PUBLIC;

        @Schema(description = "레시피 단계 본문 리스트")
        @Size(min = 1, message = "레시피 본문은 최소 1단계 이상 작성해야 합니다.")
        private List<RecipeStepDetailDto> recipeSteps;
    }

    @Getter
    @Setter
    public static class Patch {

        @Schema(description = "레시피 게시글 제목", example = "존나 맛있는 공기밥 레시피")
        @Size(min = 1, max = 20, message = "제목은 1자 이상 20자 이내여야 합니다.")
        @Pattern(
                regexp = "^(?!\\s)(?!.*\\s{2,}).*$",
                message = "제목은 공백으로 시작하거나 연속된 공백이 포함될 수 없습니다."
        )
        private String title;

        @Schema(description = "레시피 대표 사진 URL", example = "https://updated-image-url.jpg")
        private String image;

        @Schema(description = "레시피 상태", example = "RECIPE_PRIVATE")
        private RecipeBoard.RecipeStatus recipeStatus;

        @Schema(description = "레시피 단계 본문 리스트")
        private List<RecipeStepDetailDto> recipeSteps;
    }

    @Getter
    @Builder
    public static class Response {

        @Schema(description = "레시피 게시글 ID", example = "1")
        private Long recipeBoardId;

        @Schema(description = "게시글 제목", example = "공기밥 짱맛 레시피")
        private String title;

        @Schema(description = "메뉴 이름", example = "공기밥")
        private String menuName;

        @Schema(description = "대표 이미지", example = "https://image.url")
        private String image;

        @Schema(description = "게시글 공개 상태", example = "RECIPE_PUBLIC")
        private RecipeBoard.RecipeStatus recipeStatus;

        @Schema(description = "게시글 상태", example = "RECIPE_BOARD_POST")
        private RecipeBoard.RecipeBoardStatus recipeBoardStatus;

        @Schema(description = "작성자 닉네임", example = "요리왕김요리")
        private String nickName;

        @Schema(description = "작성 일시", example = "2024-04-08T12:34:56")
        private LocalDateTime createdAt;

        @Schema(description = "레시피 단계 리스트")
        private List<RecipeStepDetailDto.Response> recipeSteps;
    }
}
