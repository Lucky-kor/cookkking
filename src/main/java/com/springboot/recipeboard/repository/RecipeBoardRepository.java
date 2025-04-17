package com.springboot.recipeboard.repository;

import com.springboot.member.entity.Member;
import com.springboot.recipeboard.entity.RecipeBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeBoardRepository extends JpaRepository<RecipeBoard, Long> {
    Page<RecipeBoard> findByMemberAndRecipeBoardStatusNot(Member member, RecipeBoard.RecipeBoardStatus status, Pageable pageable);
}
