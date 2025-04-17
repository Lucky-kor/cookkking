package com.springboot.collection.repository;

import com.springboot.collection.entity.CollectionItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CollectionItemRepository extends JpaRepository<CollectionItem, Long> {
}
