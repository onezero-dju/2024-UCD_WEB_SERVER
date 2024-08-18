package com.ucd.keynote.domain.user.entity;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;


@Embeddable
@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class UserOrganizationId implements Serializable {
    private Long userId;
    private Long organizationId;

    public UserOrganizationId(Long userId, Long organizationId){
        this.userId = userId;
        this.organizationId = organizationId;
    }
}
