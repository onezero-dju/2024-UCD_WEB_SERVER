package com.ucd.keynote.domain.category.repository;

import com.ucd.keynote.domain.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
