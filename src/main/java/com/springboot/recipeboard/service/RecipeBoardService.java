package com.springboot.recipeboard.service;

import com.springboot.exception.BusinessLogicException;
import com.springboot.exception.ExceptionCode;
import com.springboot.member.entity.Member;
import com.springboot.member.repository.MemberRepository;
import com.springboot.menu.entity.Menu;
import com.springboot.menu.repository.MenuRepository;
import com.springboot.recipeboard.entity.RecipeBoard;
import com.springboot.recipeboard.repository.RecipeBoardRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class RecipeBoardService {
    private final MemberRepository memberRepository;
    private final MenuRepository menuRepository;
    private final RecipeBoardRepository recipeBoardRepository;

    public RecipeBoardService(MemberRepository memberRepository, MenuRepository menuRepository, RecipeBoardRepository recipeBoardRepository) {
        this.memberRepository = memberRepository;
        this.menuRepository = menuRepository;
        this.recipeBoardRepository = recipeBoardRepository;
    }

    // 게시글 등록
    public RecipeBoard createRecipeBoard(RecipeBoard recipeBoard, long memberId) {
        return null;
    }

    // 게시글 수정
    public RecipeBoard updateRecipeBoard(RecipeBoard recipeBoard, long memberId) {
        return null;
    }

    // 게시글 단일 조회
    public RecipeBoard findRecipeBoard(long recipeBoardId, long memberId) {
        return null;
    }

    // 게시글 삭제
    public void deleteRecipeBoard(long recipeBoardId, long memberId) {

    }

    // 카테고리별 레시피 게시글 전체 조회
    public Page<RecipeBoard> findCategoryRecipeBoards(int page, int size, long recipeBoardId, long memberId) {
        return null;
    }

    // 메뉴별 레시피 게시글 전체 조회
    public Page<RecipeBoard> findMenuRecipeBoards(int page, int size, long recipeBoardId, long memberId) {
        return null;
    }

    // 레시피 게시글 북마크 추가/해제
    public void toggleBookmark(long recipeBoardId, long memberId) {

    }

    // 레시피 게시글 검색
    public Page<RecipeBoard> recipeBoardSearch(long recipeBoardId, long memberId, String keyword) {
        return null;
    }

    // 회원 존재 여부 검증
    public Member verifyMemberExists(long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }

    // 메뉴 존재 여부 검증
    public Menu verifyMenuExists(String menuName) {
        return menuRepository.findByMenuName(menuName)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MENU_NOT_FOUND));
    }

    // 게시글 존재 여부 검증
    public RecipeBoard verifyRecipeBoardExists(long recipeBoardId) {
        return recipeBoardRepository.findById(recipeBoardId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.RECIPEBOARD_NOT_FOUND));
    }

    // 게시글 작성자인지 검증
    public void verifyIsOwner(RecipeBoard recipeBoard, long memberId) {
        if (recipeBoard.getMember().getMemberId() != memberId) {
            throw new BusinessLogicException(ExceptionCode.MEMBER_NOT_MATCH);
        }
    }

    // 비공개 게시글 접근 권한 검증
    public void verifyCanAccessPrivate(RecipeBoard recipeBoard, long memberId) {
        if (recipeBoard.getRecipeStatus() == RecipeBoard.RecipeStatus.RECIPE_PRIVATE &&
                recipeBoard.getMember().getMemberId() != memberId) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED_ACCESS);
        }
    }

    // 검색 키워드 유효성 검증
    public void verifySearchKeyword(String keyword) {
        if (keyword == null || keyword.trim().isEmpty()) {
            throw new BusinessLogicException(ExceptionCode.INVALID_KEYWORD);
        }
    }

}
