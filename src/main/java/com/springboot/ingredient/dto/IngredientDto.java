package com.springboot.ingredient.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class IngredientDto {
    @Getter
    @Setter
    @NoArgsConstructor
    public static class IdRequest {
        private long ingredientId;
        private String type;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Post {
        private String ingredientName;
        private String image;
        private String mainCategory;
        private String subCategory;
        private String dtype; // "MAIN" or "SEASONING"
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Patch {
        private long ingredientId;
        private String ingredientName;
        private String image;
        private String mainCategory;
        private String subCategory;
        // 보통 dtype은 수정 불가, 수정이 아닌 현재 상태를 나타내기 위해 사용
        private String dtype;
    }

    @Getter
    @AllArgsConstructor
    public static class Response {
        private long ingredientId;
        private String ingredientName;
        private String image;
        private String mainCategory;
        private String subCategory;
        private String dtype;
    }
}
