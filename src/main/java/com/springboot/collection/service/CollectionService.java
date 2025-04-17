package com.springboot.collection.service;

import com.springboot.collection.entity.Collection;
import com.springboot.collection.entity.CollectionItem;
import com.springboot.collection.repository.CollectionItemRepository;
import com.springboot.collection.repository.CollectionRepository;
import com.springboot.exception.BusinessLogicException;
import com.springboot.exception.ExceptionCode;
import com.springboot.member.entity.Member;
import com.springboot.member.repository.MemberRepository;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class CollectionService {
    private final MemberRepository memberRepository;
    private final CollectionRepository collectionRepository;
    private final CollectionItemRepository collectionItemRepository;

    public CollectionService(MemberRepository memberRepository, CollectionRepository collectionRepository, CollectionItemRepository collectionItemRepository) {
        this.memberRepository = memberRepository;
        this.collectionRepository = collectionRepository;
        this.collectionItemRepository = collectionItemRepository;
    }


    // 도감 카테고리 생성
    public Collection createCollection(Collection collection, long memberId) {
        return null;
    }

    // 도감 카테고리 수정
    public Collection updateCollection(Collection collection, long memberId) {
        return null;
    }

    // 도감 카테고리 전체 조회
    public Page<Collection> findCollections(int page, int size, long memberId) {
        return null;
    }

    // 도감 카테고리 삭제
    public void deleteCollection(long collectionId, long memberId) {

    }

    // 도감 카테고리 내 메뉴 목록 조회
    public Page<CollectionItem> findCollectionItems(long collectionId, int page, int size, long memberId) {
        return null;
    }

    // 도감 카테고리 메뉴 추가
    public CollectionItem addCollectionItem(long collectionId, CollectionItem collectionItem, long memberId) {
        return null;
    }

    // 도감 카테고리 메뉴 삭제
    public void deleteCollectionItem(long collectionItemId, long memberId) {

    }

    // 회원 존재 확인
    public Member verifyMemberExists(long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.MEMBER_NOT_FOUND));
    }

    // 도감 존재 확인
    public Collection verifyCollectionExists(long collectionId) {
        return collectionRepository.findById(collectionId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.COLLECTION_NOT_FOUND));
    }

    // 도감 소유자 검증
    public void verifyCollectionOwner(Collection collection, long memberId) {
        if (collection.getMember().getMemberId() != memberId) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED_ACCESS);
        }
    }

    // 도감 + 소유자 한 번에 검증
    public Collection verifyOwnedCollection(long collectionId, long memberId) {
        Collection collection = verifyCollectionExists(collectionId);
        verifyCollectionOwner(collection, memberId);
        return collection;
    }

    // 도감 메뉴 존재 확인
    public CollectionItem verifyCollectionItemExists(long collectionItemId) {
        return collectionItemRepository.findById(collectionItemId)
                .orElseThrow(() -> new BusinessLogicException(ExceptionCode.COLLECTION_ITEM_NOT_FOUND));
    }

    // 도감 메뉴 소유자 확인
    public void verifyCollectionItemOwner(Collection collection, long memberId) {
        if (collection.getMember().getMemberId() != memberId) {
            throw new BusinessLogicException(ExceptionCode.UNAUTHORIZED_ACCESS);
        }
    }
}

