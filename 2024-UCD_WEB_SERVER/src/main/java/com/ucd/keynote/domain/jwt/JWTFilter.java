package com.ucd.keynote.domain.jwt;

import com.ucd.keynote.domain.user.dto.CustomUserDetails;
import com.ucd.keynote.domain.user.entity.UserEntity;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

public class JWTFilter extends OncePerRequestFilter {
    private final JWTUtil jwtUtil;
    private final List<String> excludedPaths = List.of("/login", "/api/users/signup");
    public JWTFilter(JWTUtil jwtUtil) {

        this.jwtUtil = jwtUtil;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String requestPath = request.getServletPath();

        // 특정 경로에 대해서는 필터를 적용하지 않음
        if (excludedPaths.contains(requestPath)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 쿠키에서 JWT 토큰 추출
        String token = null;
        if (request.getCookies() != null) {
            for (Cookie cookie : request.getCookies()) {
                if ("token".equals(cookie.getName())) {
                    token = cookie.getValue();
                    break;
                }
            }
        }

        // 토큰이 없거나 만료된 경우
        if (token == null || jwtUtil.isExpired(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        // 토큰에서 사용자 정보 추출
        String email = jwtUtil.getEmail(token);
        String username = jwtUtil.getUserName(token);
        String role = jwtUtil.getRole(token);

        // UserEntity 객체 생성 (또는 로드)
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(email);
        userEntity.setUsername(username);
        userEntity.setRole(role);

        // CustomUserDetails 객체 생성
        CustomUserDetails userDetails = new CustomUserDetails(userEntity);

        // 인증 객체 생성 및 컨텍스트에 설정
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(userDetails, null, jwtUtil.getAuthorities(role));
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // 다음 필터로 요청 전달
        filterChain.doFilter(request, response);
    }
}
