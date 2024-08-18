package com.ucd.keynote.domain.user.service;

import com.ucd.keynote.domain.user.dto.JoinDTO;
import com.ucd.keynote.domain.user.entity.UserEntity;
import com.ucd.keynote.domain.user.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class JoinService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    public JoinService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {

        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public void joinProcess(JoinDTO joinDTO) {

        String name = joinDTO.getName();
        String password = joinDTO.getPassword();
        String email = joinDTO.getEmail();

        // 이메일 존재하는지 확인
        Boolean isExist = userRepository.existsByEmail(email);

        if (isExist) {
            // 이메일 존재할 시 예외 처리
            return;
        }

        UserEntity data = new UserEntity();

        data.setName(name);
        data.setPassword(bCryptPasswordEncoder.encode(password)); // 암호화를 진행하여 저장
        data.setEmail(email);
        data.setRole("ROLE_ADMIN");

        userRepository.save(data);
    }
}
