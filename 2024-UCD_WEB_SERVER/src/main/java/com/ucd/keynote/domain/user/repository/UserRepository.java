package com.ucd.keynote.domain.user.repository;

import com.ucd.keynote.domain.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
    Boolean existsByEmail(String email); // 유저가 존재 하는지 확인 하는 메서드

    UserEntity findByEmail(String email); //email를 받아 DB 테이블에서 회원을 조회하는 메서드 작성
}
