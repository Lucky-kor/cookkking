package com.springboot.member.entity;

import com.springboot.title.entity.Title;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class MemberTitle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long memberTitleId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "title_id")
    private Title title;

    public void setMember(Member member) {
        this.member = member;
        if (!member.getMemberTitles().contains(this)) {
            member.setMemberTitle(this);
        }
    }
}
