package com.springboot.collection.entity;

import com.springboot.member.entity.Member;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class Collection {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long collectionId;

    @Column(nullable = false)
    private String customCategoryName;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CollectionStatus collectionStatus = CollectionStatus.PUBLIC;

    @OneToMany(mappedBy = "collection", cascade = CascadeType.PERSIST)
    private List<CollectionItem> collectionItems = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    public void setMember(Member member) {
        this.member = member;
        if (!member.getCollections().contains(this)) {
            member.setCollection(this);
        }
    }

    public void setCollectionItem(CollectionItem collectionItem) {
        collectionItems.add(collectionItem);
        if (collectionItem.getCollection() != this) {
            collectionItem.setCollection(this);
        }
    }

    public enum CollectionStatus {
        PUBLIC("도감 공개"),
        PRIVATE("도감 비공개");

        @Getter
        private String status;

        CollectionStatus(String status) {
            this.status = status;
        }
    }
}
