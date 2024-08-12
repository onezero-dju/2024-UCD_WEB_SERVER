package com.ucd.keynote.domain.organization.controller;

import com.ucd.keynote.domain.organization.dto.OrganizationDto;
import com.ucd.keynote.domain.organization.service.OrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/organizations")
public class OrganizationController {
    private final OrganizationService organizationService;
    @Autowired
    public OrganizationController(OrganizationService organizationService){
        this.organizationService = organizationService;
    }

    @PostMapping
    public ResponseEntity<OrganizationDto> createdOrganization(@RequestBody OrganizationDto organizationDto){
        // 조직 생성 요청을 처리하고 생성된 조직 정보를 반환
        OrganizationDto createdOrganization = organizationService.createOrganization(organizationDto);
        return new ResponseEntity<>(createdOrganization, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrganizationDto> getOrganization(@PathVariable Long id){
        // ID로 조직 조회 요청을 처리하고 조직 정보를 반환
        OrganizationDto organizationDto = organizationService.getOrganization(id);
        return ResponseEntity.ok(organizationDto);
    }
}
