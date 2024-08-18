package com.ucd.keynote.domain.user.repository;

import com.ucd.keynote.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByEmail(String email);
    Boolean existsByEmail(String email); // 유저가 존재 하는지 확인 하는 메서드

    Boolean existsByName(String name);
}
