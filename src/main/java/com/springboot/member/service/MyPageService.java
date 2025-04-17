package com.springboot.member.service;

import com.springboot.bookmark.repository.BookmarkRepository;
import com.springboot.exception.BusinessLogicException;
import com.springboot.exception.ExceptionCode;
import com.springboot.member.entity.Member;
import com.springboot.member.entity.MemberTheme;
import com.springboot.member.entity.MemberTitle;
import com.springboot.member.repository.MemberRepository;
import com.springboot.recipeboard.entity.RecipeBoard;
import com.springboot.recipeboard.repository.RecipeBoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyPageService {
    private final MemberService memberService;
    private final MemberRepository memberRepository;
    private final BookmarkRepository bookmarkRepository;
    private final RecipeBoardRepository recipeBoardRepository;

    public MyPageService(MemberService memberService, MemberRepository memberRepository, BookmarkRepository bookmarkRepository, RecipeBoardRepository recipeBoardRepository) {
        this.memberService = memberService;
        this.memberRepository = memberRepository;
        this.bookmarkRepository = bookmarkRepository;
        this.recipeBoardRepository = recipeBoardRepository;
    }

    // 내 정보 조회
    public Member findMyInfo(long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }

    // 내 북마크 리스트 조회
    public Page<RecipeBoard> findMyBookmarkList(long memberId, int page, int size) {
        Member member = memberService.findMember(memberId);

        return bookmarkRepository.findRecipeBoardsByMember(member,
                PageRequest.of(page, size, Sort.by("recipeBoard.recipeBoardId").descending()));
    }

    // 내 북마크 해제
    public void deleteMyBookmark(long memberId) {

    }

    // 내 보유 칭호 전체 조회
    public List<MemberTitle> findMyTitle(long memberId) {
        return null;
    }

    // 내 레시피 게시글 조회
    public Page<RecipeBoard> findMyRecipeBoards(long memberId, int page, int size) {
        // 회원 검증
        Member member = memberService.findMember(memberId);

        // 본인이 작성한 게시글 조회 (삭제 상태 제외하고)
        return recipeBoardRepository.findByMemberAndRecipeBoardStatusNot(
                member,
                RecipeBoard.RecipeBoardStatus.RECIPE_BOARD_DELETE,  // 만약 삭제 상태가 있다면 필터링, 없으면 이 조건 제거
                PageRequest.of(page - 1, size, Sort.by("recipeBoardId").descending())
        );
    }

    // 내 테마 리스트 조회
    public List<MemberTheme> findMyTheme(long memberId) {
        return null;
    }

    // 내 레시피 게시글 검색
    public List<RecipeBoard> findSearchMyRecipeBoard(long memberId, String keyword) {
        return null;
    }
}
