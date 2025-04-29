package com.springboot.profile.repository;

import com.springboot.member.entity.MemberProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberProfileImageRepository extends JpaRepository<MemberProfileImage, Long> {
}
