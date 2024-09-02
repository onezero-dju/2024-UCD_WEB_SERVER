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


        String name = SecurityContextHolder.getContext().getAuthentication().getName();




        String email = null;
        // String username = null;

        // 현재 인증된 사용자 정보 가져오기
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null && authentication.getPrincipal() instanceof UserDetails) {
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();
            // 이메일을 바로 가져오기
            if (userDetails instanceof CustomUserDetails) { // CustomUserDetails는 커스텀 유저 클래스 이름입니다.
                email = ((CustomUserDetails) userDetails).getEmail();
                // username = ((CustomUserDetails) userDetails).getUsername();
            } else {
                // userDetails.getUsername()이 이메일이라면 이 코드 사용
                email = userDetails.getUsername();
            }
        }

        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Iterator<? extends GrantedAuthority> iter = authorities.iterator();
        GrantedAuthority auth = iter.next();
        String role = auth.getAuthority();

        return "main Controller " + email + role;
    }
}