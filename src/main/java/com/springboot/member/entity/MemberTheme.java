package com.springboot.member.entity;

import com.springboot.theme.entity.Theme;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class MemberTheme {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long memberThemeId;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "theme_id")
    private Theme theme;

    public void setMember(Member member) {
        this.member = member;
        if (!member.getMemberThemes().contains(this)) {
            member.setMemberTheme(this);
        }
    }
}
