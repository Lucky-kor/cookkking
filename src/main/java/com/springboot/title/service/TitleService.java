package com.springboot.title.service;

import com.springboot.challenge.entity.ChallengeLevel;
import com.springboot.challenge.repository.MemberChallengeRepository;
import com.springboot.exception.BusinessLogicException;
import com.springboot.exception.ExceptionCode;
import com.springboot.member.entity.Member;
import com.springboot.member.entity.MemberChallenge;
import com.springboot.member.entity.MemberTitle;
import com.springboot.member.repository.MemberRepository;
import com.springboot.title.entity.Title;
import com.springboot.title.repository.ChallengeLevelRepository;
import com.springboot.title.repository.TitleRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TitleService {
    private final TitleRepository titleRepository;
    private final MemberChallengeRepository memberChallengeRepository;
    private final ChallengeLevelRepository challengeLevelRepository;
    private final MemberRepository memberRepository;

    public TitleService(TitleRepository titleRepository, MemberChallengeRepository memberChallengeRepository, ChallengeLevelRepository challengeLevelRepository, MemberRepository memberRepository) {
        this.titleRepository = titleRepository;
        this.memberChallengeRepository = memberChallengeRepository;
        this.challengeLevelRepository = challengeLevelRepository;
        this.memberRepository = memberRepository;
    }

    public Page<Title> findTitles(int page, int size, Long memberId) {
        return titleRepository.findAll(PageRequest.of(page, size, Sort.by("titleId").ascending()));
    }
    public Page<Title> findOwnedTitles(int page, int size, Long memberId) {
        return titleRepository.findByMemberTitles_Member_MemberId(memberId, PageRequest.of(page, size, Sort.by("titleId").ascending()));
    }

    public Page<Title> findUnownedTitles(int page, int size, Long memberId) {
        return titleRepository.findTitlesNotOwnedByMember(memberId, PageRequest.of(page, size, Sort.by("titleId").ascending()));
    }

    public void incrementChallengeCount(long memberChallengeId, long memberId) {
        // MemberChallengeId를 통해 MemberChallenge 객체를 가져옴
        MemberChallenge memberChallenge = memberChallengeRepository.findById(memberChallengeId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_CHALLENGE_NOT_FOUND));
        // 현재 카운트를 증가시킴
        int count = memberChallenge.getCurrentCount() + 1;
        memberChallenge.setCurrentCount(count);
        // 도전과제에서 할당된 전체 카운트에 도달했는지 확인하기 위해, 연관된 ChallengeLevel을 가져옴
        long challengeCategoryId = memberChallenge.getChallengeCategory().getChallengeCategoryid();
        ChallengeLevel challengeLevel = challengeLevelRepository.findByChallengeCategory_ChallengeCategoryidAndLevel(challengeCategoryId, memberChallenge.getCurrentLevel())
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.CHALLENGE_CATEGORY_NOT_FOUND));
        // 도전과제의 목표 카운트에 도달했는지 확인
        if(count == challengeLevel.getGoalCount()) {
            Title title = titleRepository.findByChallengeCategory_ChallengeCategoryidAndLevel(challengeCategoryId, challengeLevel.getLevel())
                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.TITLE_NOT_FOUND));
            // 도전과제의 목표 카운트에 도달했을 때, 타이틀을 부여
            MemberTitle memberTitle = new MemberTitle();
            memberTitle.setMember(memberChallenge.getMember());
            memberTitle.setTitle(title);
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
            member.setMemberTitle(memberTitle);
            // 타이틀을 부여한 후, MemberChallenge의 currentCount를 업데이트
//            memberRepository.save(member);

            // memberChallenge의 현재 레벨을 업데이트 해야 함.
            // title 테이블에서 challenge_category_id를 통해 해당 타이틀에서 더 높은 레벨의 데이터가 있는지 확인하고
            // 있다면 memberChallenge의 challengeLevel을 1 증가
            // 업다면 최대 레벨에 도달했으므로 그냥 둠.
            Optional<Title> nextTitle = titleRepository.findByChallengeCategory_ChallengeCategoryidAndLevel(challengeCategoryId, challengeLevel.getLevel() + 1);
            if(nextTitle.isPresent()) {
                // 다음 레벨이 존재하면, memberChallenge의 challengeLevel을 1 증가
                memberChallenge.setCurrentLevel(challengeLevel.getLevel() + 1);
            }
        }

        memberChallengeRepository.save(memberChallenge);
    }

    public void decrementChallengeCount(long memberChallengeId, long memberId) {
        // MemberChallengeId를 통해 MemberChallenge 객체를 가져옴
        MemberChallenge memberChallenge = memberChallengeRepository.findById(memberChallengeId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_CHALLENGE_NOT_FOUND));
        // 현재 카운트를 증가시킴
        int count = memberChallenge.getCurrentCount() - 1;
        memberChallenge.setCurrentCount(count);
        // 도전과제에서 할당된 전체 카운트에 도달했는지 확인하기 위해, 연관된 ChallengeLevel을 가져옴
        long challengeCategoryId = memberChallenge.getChallengeCategory().getChallengeCategoryid();

        memberChallengeRepository.save(memberChallenge);
    }

    public void incrementChallengeCostCount(long memberChallengeId, long memberId, int cost) {
        // MemberChallengeId를 통해 MemberChallenge 객체를 가져옴
        MemberChallenge memberChallenge = memberChallengeRepository.findById(memberChallengeId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_CHALLENGE_NOT_FOUND));
        // 현재 카운트를 증가시킴
        int count = memberChallenge.getCurrentCount() + cost;
        memberChallenge.setCurrentCount(count);
        // 도전과제에서 할당된 전체 카운트에 도달했는지 확인하기 위해, 연관된 ChallengeLevel을 가져옴
        long challengeCategoryId = memberChallenge.getChallengeCategory().getChallengeCategoryid();
        ChallengeLevel challengeLevel = challengeLevelRepository.findByChallengeCategory_ChallengeCategoryidAndLevel(challengeCategoryId, memberChallenge.getCurrentLevel())
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.CHALLENGE_CATEGORY_NOT_FOUND));
        // 도전과제의 목표 카운트에 도달했는지 확인
        if(count == challengeLevel.getGoalCount()) {
            Title title = titleRepository.findByChallengeCategory_ChallengeCategoryidAndLevel(challengeCategoryId, challengeLevel.getLevel())
                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.TITLE_NOT_FOUND));
            // 도전과제의 목표 카운트에 도달했을 때, 타이틀을 부여
            MemberTitle memberTitle = new MemberTitle();
            memberTitle.setMember(memberChallenge.getMember());
            memberTitle.setTitle(title);
            Member member = memberRepository.findById(memberId)
                    .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
            member.setMemberTitle(memberTitle);
            // 타이틀을 부여한 후, MemberChallenge의 currentCount를 업데이트
//            memberRepository.save(member);

            // memberChallenge의 현재 레벨을 업데이트 해야 함.
            // title 테이블에서 challenge_category_id를 통해 해당 타이틀에서 더 높은 레벨의 데이터가 있는지 확인하고
            // 있다면 memberChallenge의 challengeLevel을 1 증가
            // 업다면 최대 레벨에 도달했으므로 그냥 둠.
            Optional<Title> nextTitle = titleRepository.findByChallengeCategory_ChallengeCategoryidAndLevel(challengeCategoryId, challengeLevel.getLevel() + 1);
            if(nextTitle.isPresent()) {
                // 다음 레벨이 존재하면, memberChallenge의 challengeLevel을 1 증가
                memberChallenge.setCurrentLevel(challengeLevel.getLevel() + 1);
            }
        }

        memberChallengeRepository.save(memberChallenge);
    }
}