package com.ucd.keynote.domain.organization.service;

import com.ucd.keynote.domain.organization.dto.OrganizationDto;
import com.ucd.keynote.domain.organization.entity.OrganizationEntity;
import com.ucd.keynote.domain.organization.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class OrganizationService {
    private OrganizationRepository organizationRepository;
    @Autowired
    public OrganizationService(OrganizationRepository organizationRepository){
        this.organizationRepository = organizationRepository;
    }

    // 조직 생성
    public OrganizationDto createOrganization(OrganizationDto organizationDto){
        // DTO 에서 엔티티로 변환하여 데이터베이스에 저장
        OrganizationEntity organizationEntity = new OrganizationEntity();
        organizationEntity.setName(organizationDto.getName());
        organizationEntity.setDescription(organizationDto.getDescription());
        organizationRepository.save(organizationEntity);

        // 저장된 엔티티의 ID를 DTO에 설정하여 반환
        organizationDto.setOrganizationId(organizationEntity.getOrganizationId());
        return organizationDto;
    }

    // 조직 조회
    public OrganizationDto getOrganization(Long id){
        Optional<OrganizationEntity> organizationEntity = organizationRepository.findById(id);
        if(organizationEntity.isPresent()){
            OrganizationDto organizationDto = new OrganizationDto();
            organizationDto.setOrganizationId(organizationEntity.get().getOrganizationId());
            organizationDto.setName(organizationEntity.get().getName());
            organizationDto.setDescription(organizationEntity.get().getDescription());
            return organizationDto;
        } else {
            throw new RuntimeException("Organization not found");
        }
    }
}
