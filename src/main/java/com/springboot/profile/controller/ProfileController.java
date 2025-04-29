package com.springboot.profile.controller;

import com.springboot.dto.MultiResponseDto;
import com.springboot.dto.SingleResponseDto;
import com.springboot.member.entity.Member;
import com.springboot.member.entity.MemberProfileImage;
import com.springboot.profile.dto.ProfileDto;
import com.springboot.profile.entity.ProfileImage;
import com.springboot.profile.mapper.ProfileMapper;
import com.springboot.profile.service.ProfileService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Tag(name = "프로필", description = "프로필 관련 API")
@RestController
@RequestMapping("/profile")
public class ProfileController {
    private final ProfileService profileService;
    private final ProfileMapper profileMapper;

    public ProfileController(ProfileService profileService, ProfileMapper profileMapper) {
        this.profileService = profileService;
        this.profileMapper = profileMapper;
    }

    // 프로필 조회
     @GetMapping("/{profile-id}")
     public ResponseEntity getProfile(@Parameter(hidden = true) @AuthenticationPrincipal Member member,
                                      @PathVariable(value = "profile-id") Long profileId) {
        ProfileImage profileImage = profileService.findProfileImage(profileId);

        return new ResponseEntity(new SingleResponseDto<>(profileMapper.ProfileImageToProfileResponseDto(profileImage)), HttpStatus.OK);
     }

    // 전체 프로필 조회
    @GetMapping
    public ResponseEntity getAllProfiles(@RequestParam(defaultValue = "1") int page,
                                           @RequestParam(defaultValue = "10") int size) {
        Page<ProfileImage> profileImages = profileService.findAllProfileImages(page - 1, size);
        List<ProfileDto.Response> responseDtos = profileMapper.ProfileImageToProfileDtoList(profileImages.getContent());
        return new ResponseEntity(new MultiResponseDto<>(responseDtos, profileImages), HttpStatus.OK);
    }

    @GetMapping("members/{member-id}")
    public ResponseEntity getMemberProfiles(@RequestParam(defaultValue = "1") int page,
                                            @RequestParam(defaultValue = "10") int size,
                                            @PathVariable (value = "member-id") Long memberId) {
        Page<ProfileImage> profileImages = profileService.findMemberProfileImages(page - 1, size, memberId);
        List<ProfileDto.Response> responseDtos = profileMapper.ProfileImageToProfileDtoList(profileImages.getContent());
        return new ResponseEntity(new MultiResponseDto<>(responseDtos, profileImages), HttpStatus.OK);
    }

    // 프로필 구매
    @PostMapping("{profile-id}/purchase")
    public ResponseEntity purchaseProfile(@RequestBody @Valid ProfileDto.PurchaseRequest requestDto) {
        MemberProfileImage memberProfileImage = profileMapper.profileDtoToMemberProfileImage(requestDto);
        profileService.purchaseProfile(memberProfileImage);
        return ResponseEntity.ok().build();
    }
}
