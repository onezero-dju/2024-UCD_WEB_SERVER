package com.ucd.keynote.domain.organization.entity.join;

import com.ucd.keynote.domain.common.entity.BaseEntity;
import com.ucd.keynote.domain.organization.entity.Organization;
import com.ucd.keynote.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "organization_join_request")
@Getter
@Setter
@NoArgsConstructor
public class OrganizationJoinRequest {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "request_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @ManyToOne
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @Column(name = "status", nullable = false)
    private String status; // PENDING, APPROVED, REJECTED (대기, 승인, 거부)

    @Column(name = "request_message")
    private String requestMessage;

    @Column(name = "requested_at", nullable = false)
    private LocalDateTime requestedAt;


    // 상태 변경 메서드
    public void approveRequest() {
        this.status = "APPROVED";
    }

    public void rejectRequest() {
        this.status = "REJECTED";
    }
}
