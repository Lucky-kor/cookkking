package com.springboot.profile.repository;

import com.springboot.profile.entity.ProfileImage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProfileImageRepository extends JpaRepository<ProfileImage, Long> {
}