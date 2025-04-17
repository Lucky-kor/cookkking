package com.springboot.recipestep.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class RecipeStep { // 레시피 스텝 마스터 테이블
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long recipeStepId; // 레시피 단계 ID
    @Column(nullable = false)
    private String title; // 레시피 단계 본문
}
