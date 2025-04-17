package com.springboot.theme.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Theme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long themeId;

    @Column(nullable = false)
    private String themeName;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private String previewImage;

    @Column(nullable = false)
    private int price;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ThemeStatus themeStatus = ThemeStatus.BASIC;

    public enum ThemeStatus {
        BASIC("기본 테마"),
        JAPANESE("일본 테마"),
        CHINESE("중국 테마");

        @Getter
        private String status;

        ThemeStatus(String status) {
            this.status = status;
        }
    }
}
