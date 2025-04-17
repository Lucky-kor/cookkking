package com.springboot.collection.dto;


import com.springboot.collection.entity.Collection;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Getter
public class CollectionDto {
    @Getter
    public static class Post {
        @Schema(description = "도감 내 메뉴 이름", example = "다 태워먹은 감자전")
        @NotBlank(message = "메뉴 이름은 필수입니다.")
        @Size(min = 1, max = 20, message = "메뉴 이름은 1자 이상 20자 이내여야 합니다.")
        @Pattern(
                regexp = "^(?!\\s)(?!.*\\s{2,}).*$",
                message = "메뉴이름은 공백으로 시작하거나 연속된 공백이 포함될 수 없습니다."
        )
        private String menuName;

        @Schema(description = "도감 카테고리 이름", example = "실패해서 다시 도전할 음식")
        @NotBlank(message = "도감 카테고리 이름은 필수입니다.")
        @Size(min = 1, max = 20, message = "카테고리 이름은 1자 이상 20자 이내여야 합니다.")
        @Pattern(
                regexp = "^(?!\\s)(?!.*\\s{2,}).*$",
                message = "카테고리 이름은 공백으로 시작하거나 연속된 공백이 포함될 수 없습니다."
        )
        private String categoryName;

        @Schema(description = "도감 메뉴 대표 이미지 URL", example = "https://img.cookking.com/menu1.jpg")
        @NotBlank(message = "이미지 URL은 필수입니다.")
        private String imageUrl;
    }

    @Getter
    @Setter
    public static class Patch {
        @Schema(description = "도감 카테고리 이름", example = "실패해서 다시 도전할 음식")
        @NotBlank(message = "도감 카테고리 이름은 필수입니다.")
        @Size(min = 1, max = 20, message = "카테고리 이름은 1자 이상 20자 이내여야 합니다.")
        @Pattern(
                regexp = "^(?!\\s)(?!.*\\s{2,}).*$",
                message = "카테고리 이름은 공백으로 시작하거나 연속된 공백이 포함될 수 없습니다."
        )
        private String categoryName;

        @Schema(description = "도감 메뉴 대표 이미지 URL", example = "https://img.cookking.com/menu1.jpg")
        @NotBlank(message = "이미지 URL은 필수입니다.")
        private String imageUrl;
    }

    @Getter
    @Builder
    public static class Response {
        @Schema(description = "도감 카테고리 ID", example = "1")
        private Long collectionId;

        @Schema(description = "도감 카테고리 이름", example = "실패해서 다시 도전할 음식")
        private String categoryName;

        @Schema(description = "도감 공개 상태", example = "PUBLIC")
        private Collection.CollectionStatus collectionStatus;

        @Schema(description = "도감 생성일자", example = "2025-04-10T15:00:00")
        private LocalDateTime createdAt;

        @Schema(description = "도감에 등록된 메뉴 개수", example = "3")
        private int menuCount;

        @Schema(description = "도감에 등록된 메뉴 이미지 목록 (사용자가 직접 업로드한 이미지)",
                example = "[\"https://cookking.com/uploads/user1_menu1.jpg\", \"https://cookking.com/uploads/user1_menu2.jpg\"]")
        private List<String> userUploadedImages;
    }
}
