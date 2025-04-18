package com.springboot.category.entity;

import com.springboot.audit.BaseEntity;
import com.springboot.challenge.entity.Challenge;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class ChallengeCategory extends BaseEntity { // 챌린지 카테고리 마스터 테이블
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long challengeCategoryId;

    @Column(nullable = false)
    private String challengeCategoryName;

    @OneToMany(mappedBy = "challengeCategory", cascade = CascadeType.PERSIST)
    private List<Challenge> challenges = new ArrayList<>();

    public void setChallenge(Challenge challenge) {
        challenges.add(challenge);
        if (challenge.getChallengeCategory() != this) {
            challenge.setChallengeCategory(this);
        }
    }
}
