package com.springboot.category.repository;

import com.springboot.challenge.entity.Challenge;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChallengeCategoryRepository extends JpaRepository<Challenge, Long> {
}
