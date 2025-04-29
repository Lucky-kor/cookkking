package com.springboot.profile.entity;

import com.springboot.member.entity.MemberProfileImage;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Setter
@Getter
public class ProfileImage {
    @Id
    @GeneratedValue(strategy = javax.persistence.GenerationType.IDENTITY)
    private long profileImageId;

    private String imagePath;

    @OneToMany(cascade = CascadeType.ALL)
    private List<MemberProfileImage> memberProfileImages = new ArrayList<>();

    public void setMemberProfileImages(MemberProfileImage memberProfileImage) {
        this.memberProfileImages.add(memberProfileImage);
        if (memberProfileImage.getProfileImage() != this) {
            memberProfileImage.setProfileImage(this);
        }
    }
}