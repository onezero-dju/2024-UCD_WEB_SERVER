package com.ucd.keynote.domain.category.repository;

import com.ucd.keynote.domain.category.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRepository extends JpaRepository<Category, Long> {
    List<Category> findByChannelId_ChannelId(Long channelId);

    Boolean existsByNameAndChannelId_ChannelId(String name, Long channelId);
}
