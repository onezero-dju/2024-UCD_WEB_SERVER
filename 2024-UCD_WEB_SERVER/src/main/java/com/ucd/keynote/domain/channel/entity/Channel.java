package com.ucd.keynote.domain.channel.entity;

import com.ucd.keynote.domain.common.entity.BaseEntity;
import com.ucd.keynote.domain.organization.entity.Organization;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "channel")
public class Channel extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "channel_id")
    private Long channelId;
    @Column(nullable = false)
    private String name;
    @Column
    private String description;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private  LocalDateTime updatedAt;

}
