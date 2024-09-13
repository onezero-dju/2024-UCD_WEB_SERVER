package com.ucd.keynote.domain.category.controller;

import com.ucd.keynote.domain.category.dto.CategoryRequestDTO;
import com.ucd.keynote.domain.category.dto.CategoryResponseDTO;
import com.ucd.keynote.domain.category.service.CategoryService;
import com.ucd.keynote.domain.common.dto.ApiResponseDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/api/channels")
public class CategoryController {
    private final CategoryService categoryService;

    @PostMapping("/{channelId}/categories")
    public ResponseEntity<ApiResponseDTO<CategoryResponseDTO>> createCategory(@PathVariable Long channelId, @RequestBody CategoryRequestDTO request){

        // 카테고리 생성 서비스 호출
        CategoryResponseDTO categoryResponse = categoryService.createdCategory(request, channelId);

        // 응답 객체 생성
        ApiResponseDTO<CategoryResponseDTO> response = ApiResponseDTO.<CategoryResponseDTO>builder()
                .code(201)
                .message("Category created successfully")
                .data(categoryResponse)
                .build();

        return ResponseEntity.status(201).body(response);


    }
}
