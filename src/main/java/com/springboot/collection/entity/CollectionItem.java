package com.springboot.collection.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
public class CollectionItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long collectionItemId;

    @Column(nullable = false)
    private String name; // 음식 이름

    @Column(nullable = false)
    private String image; // 음식 사진 URL

    @ManyToOne
    @JoinColumn(name = "collection_id")
    private Collection collection;

    public void setCollection(Collection collection) {
        this.collection = collection;
        if (!collection.getCollectionItems().contains(this)) {
            collection.setCollectionItem(this);
        }
    }
}
