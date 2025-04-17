package com.springboot.challenge.entity;

import com.springboot.audit.BaseEntity;
import com.springboot.category.entity.ChallengeCategory;
import com.springboot.title.entity.Title;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Challenge extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long challengeId;

    @Column(nullable = false)
    private String challengeName;

    @Column(nullable = false)
    private String description;

    @Column(nullable = false)
    private int goalCount;

    @Column(nullable = false)
    private int difficultyLevel;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ChallengeStatus challengeStatus = ChallengeStatus.INCOMPLETE;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "title_id")
    private Title title;

    @ManyToOne
    @JoinColumn(name = "challengeCategory_id")
    private ChallengeCategory challengeCategory;

    public void setChallengeCategory(ChallengeCategory challengeCategory) {
        this.challengeCategory = challengeCategory;
        if (!challengeCategory.getChallenges().contains(this)) {
            challengeCategory.setChallenge(this);
        }
    }

    public enum ChallengeStatus {
        COMPLETE("도전과제 달성"),
        INCOMPLETE("도전과제 미달성");

        @Getter
        private String status;

        ChallengeStatus(String status) {
            this.status = status;
        }
    }
}
