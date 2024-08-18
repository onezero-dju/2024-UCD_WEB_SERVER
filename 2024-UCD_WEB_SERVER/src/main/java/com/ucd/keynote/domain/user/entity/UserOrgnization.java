package com.ucd.keynote.domain.user.entity;

import com.ucd.keynote.domain.organization.entity.OrganizationEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_organization")
@Getter
@Setter
@NoArgsConstructor
public class UserOrgnization {
    @EmbeddedId
    private UserOrganizationId id;

    @ManyToOne
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @MapsId("organizationId")
    @JoinColumn(name = "organization_id", nullable = false)
    private OrganizationEntity organization;

    @Column(nullable = false)
    private String role;

    @Column(nullable = false)
    private LocalDateTime joinedAt = LocalDateTime.now();  // 기본 가입 시간은 현재 시간으로 설정

    // 복합 키를 사용하는 생성자
    public UserOrgnization(UserEntity user, OrganizationEntity organization, String role) {
        this.user = user;
        this.organization = organization;
        this.role = role;
        this.id = new UserOrganizationId(user.getId(), organization.getId());
    }
}
