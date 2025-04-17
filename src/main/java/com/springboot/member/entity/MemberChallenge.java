package com.springboot.member.entity;

import com.springboot.challenge.entity.Challenge;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class MemberChallenge {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long memberChallengeId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "challenge_id")
    private Challenge challenge;

    public void setMember(Member member) {
        this.member = member;
        if (!member.getMemberChallenges().contains(this)) {
            member.setMemberChallenge(this);
        }
    }
}
