package com.springboot.profile.service;

import com.springboot.exception.BusinessLogicException;
import com.springboot.exception.ExceptionCode;
import com.springboot.member.entity.MemberProfileImage;
import com.springboot.profile.entity.ProfileImage;
import com.springboot.profile.repository.MemberProfileImageRepository;
import com.springboot.profile.repository.ProfileImageRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProfileService {
    private final ProfileImageRepository profileImageRepository;
    private final MemberProfileImageRepository memberProfileImageRepository;

    public ProfileService(ProfileImageRepository profileImageRepository, MemberProfileImageRepository memberProfileImageRepository) {
        this.profileImageRepository = profileImageRepository;
        this.memberProfileImageRepository = memberProfileImageRepository;
    }

    public ProfileImage findProfileImage(Long profileId) {
        Optional<ProfileImage> profileImage = profileImageRepository.findById(profileId);
        return profileImage.orElseThrow(() -> new BusinessLogicException(ExceptionCode.PROFILE_NOT_FOUND));
    }

    public Page<ProfileImage> findAllProfileImages(int page, int size) {
        return profileImageRepository.findAll(PageRequest.of(page, size, Sort.by("profileImageId").descending()));
    }

    public Page<ProfileImage> findMemberProfileImages(int page, int size, long memberId) {
        return profileImageRepository.findAllByMemberProfileImages_MemberId(memberId, PageRequest.of(page, size, Sort.by("profileImageId").descending()));
    }

    public void purchaseProfile(MemberProfileImage memberProfileImage) {
        // 프로필 구매 로직
        memberProfileImageRepository.save(memberProfileImage);
    }

}
