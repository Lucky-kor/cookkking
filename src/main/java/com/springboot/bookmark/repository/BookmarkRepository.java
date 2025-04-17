package com.springboot.bookmark.repository;

import com.springboot.bookmark.entitiy.Bookmark;
import com.springboot.member.entity.Member;
import com.springboot.recipeboard.entity.RecipeBoard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookmarkRepository extends JpaRepository<Bookmark, Long> {
    Page<RecipeBoard> findRecipeBoardsByMember(Member member, Pageable pageable);
}
