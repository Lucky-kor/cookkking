package com.springboot.profile.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class ProfileDto {
    @Getter
    @Setter
    @NoArgsConstructor
    public static class Response {
        private long profileImageId;
        private String imagePath;
    }

    @Getter
    @Setter
    @NoArgsConstructor
    public static class PurchaseRequest {
        private Long profileId;
        private Long memberId;
    }
}
