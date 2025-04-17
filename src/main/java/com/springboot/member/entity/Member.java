package com.springboot.member.entity;

import com.springboot.audit.BaseEntity;
import com.springboot.bookmark.entitiy.Bookmark;
import com.springboot.collection.entity.Collection;
import com.springboot.payment.entity.Payment;
import com.springboot.recipeboard.entity.RecipeBoard;
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
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long memberId;

    @Column(nullable = false, unique = true)
    private String loginId;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String nickName;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String phoneNumber;

    @Column
    private String profile;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> roles = new ArrayList<>();

    @Column
    private String title;

    @Column(nullable = false)
    private int ricePoint = 0; // 현재 보유 밥풀

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private MemberStatus memberStatus = MemberStatus.MEMBER_ACTIVE;

    @OneToMany(mappedBy = "member", cascade = CascadeType.PERSIST)
    private List<Collection> collections = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.PERSIST)
    private List<RecipeBoard> recipeBoards = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.PERSIST)
    private List<Bookmark> bookmarks = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<MemberTheme> memberThemes = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<MemberTitle> memberTitles = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    private List<MemberChallenge> memberChallenges = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = {CascadeType.PERSIST})
    private List<Payment> payments = new ArrayList<>();

    public void setCollection(Collection collection) {
        collections.add(collection);
        if (collection.getMember() != this) {
            collection.setMember(this);
        }
    }

    public void setRecipeBoard(RecipeBoard recipeBoard) {
        recipeBoards.add(recipeBoard);
        if (recipeBoard.getMember() != this) {
            recipeBoard.setMember(this);
        }
    }

    public void setBookmark(Bookmark bookmark) {
        bookmarks.add(bookmark);
        if (bookmark.getMember() != this) {
            bookmark.setMember(this);
        }
    }

    public void setMemberTheme(MemberTheme memberTheme) {
        memberThemes.add(memberTheme);
        if (memberTheme.getMember() != this) {
            memberTheme.setMember(this);
        }
    }

    public void setMemberTitle(MemberTitle memberTitle) {
        memberTitles.add(memberTitle);
        if (memberTitle.getMember() != this) {
            memberTitle.setMember(this);
        }
    }

    public void setMemberChallenge(MemberChallenge memberChallenge) {
        memberChallenges.add(memberChallenge);
        if (memberChallenge.getMember() != this) {
            memberChallenge.setMember(this);
        }
    }

    public void setPayment(Payment payment) {
        payments.add(payment);
        if (payment.getMember() != this) {
            payment.setMember(this);
        }
    }

    public enum MemberStatus {
        MEMBER_ACTIVE("활동 상태"),
        MEMBER_QUIT("탈퇴 상태");

        @Getter
        private String status;

        MemberStatus(String status) {
            this.status = status;
        }
    }
}
