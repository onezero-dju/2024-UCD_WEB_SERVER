package com.ucd.keynote.domain.category.service;

import com.ucd.keynote.domain.category.dto.CategoryRequestDTO;
import com.ucd.keynote.domain.category.dto.CategoryResponseDTO;
import com.ucd.keynote.domain.category.entity.Category;
import com.ucd.keynote.domain.category.repository.CategoryRepository;
import com.ucd.keynote.domain.channel.dto.ChannelResponseDTO;
import com.ucd.keynote.domain.channel.entity.Channel;
import com.ucd.keynote.domain.channel.repository.ChannelRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class CategoryService {
    private final ChannelRepository channelRepository;
    private final CategoryRepository categoryRepository;

    // 카테고리 생성
    public CategoryResponseDTO createdCategory(CategoryRequestDTO request, Long channelId){
        // 채널 존재 여부 확인
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new RuntimeException("채널이 존재하지 않습니다."));

        // 카테고리 생성
        Category category = new Category();
        category.setName(request.getName());
        category.setChannelId(channel);
        category.setCreatedAt(LocalDateTime.now());
        category.setUpdatedAt(LocalDateTime.now());

        // 카테고리 DB에 저장
        categoryRepository.save(category);

        // 응답 객체 생성 및 반환
        CategoryResponseDTO response = CategoryResponseDTO.builder()
                .categoryId(category.getCategoryId())
                .name(category.getName())
                .channelId(channel.getChannelId())
                .createdAt(category.getCreatedAt())
                .build();

        return response;
    }

    // 카테고리 조회
    public List<CategoryResponseDTO> getCategories(Long channelId){
        // 채널 존재 여부 확인
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new RuntimeException("채널이 존재하지 않습니다."));

        List<Category> categories = categoryRepository.findByChannelId_ChannelId(channelId);

        List<CategoryResponseDTO> response = categories.stream()
                .map(category -> CategoryResponseDTO.builder()
                        .categoryId(category.getCategoryId())
                        .name(category.getName())
                        .channelId(channel.getChannelId())
                        .createdAt(category.getCreatedAt())
                        .build())
                .collect(Collectors.toList());

        return response;
    }

    // 카테고리 수정
    public  CategoryResponseDTO updateCategory(Long channelId, Long categoryId, CategoryRequestDTO request){
        // 카테고리 받아오기

        // 채널 존재 여부 확인
        Channel channel = channelRepository.findById(channelId)
                .orElseThrow(() -> new RuntimeException("채널이 존재하지 않습니다."));

        // 카테고리 존재 여부 확인
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("카테고리가 존재하지 않습니다."));

        // 동일한 채널 내에서 중복된 카테고리 이름 확인
        boolean exists = categoryRepository.existsByNameAndChannelId_ChannelId(request.getName(), channelId);
        if (exists) {
            throw new RuntimeException("카테고리 이름이 중복됩니다.");
        }

        // 카테고리 이름 및 설명 수정
        category.setName(request.getName());
        category.setUpdatedAt(LocalDateTime.now());

        // DB에 저장
        categoryRepository.save(category);

        CategoryResponseDTO response = CategoryResponseDTO.builder()
                .categoryId(category.getCategoryId())
                .name(category.getName())
                .createdAt(category.getCreatedAt())
                .build();

        return response;
    }


    // 카테고리 삭제


}
