package com.springboot.profile.repository;

import com.springboot.profile.entity.ProfileImage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {
    Page<ProfileImage> findAllByMemberProfileImages_MemberId(Long memberId, Pageable pageable);
}