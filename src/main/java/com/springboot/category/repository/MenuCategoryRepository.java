package com.springboot.category.repository;

import com.springboot.menu.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuCategoryRepository extends JpaRepository<Menu, Long> {
}
