package com.ucd.keynote.domain.user.controller;

import com.ucd.keynote.domain.user.dto.CustomUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
import java.util.Iterator;

@Controller
@ResponseBody
public class MainController {

    @GetMapping("/")
    public String mainP() {
// 현재 인증된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // 사용자 이메일과 역할 가져오기
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String email = userDetails.getEmail(); // 인증 객체의 name은 이메일이 됩니다.
        String username = userDetails.getUsername();
        String role = authentication.getAuthorities().iterator().next().getAuthority(); // 권한 중 첫 번째 권한을 가져옴

        // 이메일과 역할을 포함한 메시지 반환
        return "User email: " + email + ", Role: " + role + ", User name: " + username;
    }
}