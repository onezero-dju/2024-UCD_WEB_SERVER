package com.ucd.keynote.domain.organization.entity;

import com.ucd.keynote.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "user_organization")
public class UserOrganization {
    @EmbeddedId
    private UserOrganizationId id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("userId")
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("organizationId")
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;


    @Column(name = "role", nullable = false)
    private String role;

    @Column(name = "joined_at")
    private LocalDateTime joinedAt;

}
