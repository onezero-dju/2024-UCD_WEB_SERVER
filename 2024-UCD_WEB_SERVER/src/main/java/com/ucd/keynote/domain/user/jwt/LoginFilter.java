package com.ucd.keynote.domain.user.jwt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ucd.keynote.domain.common.dto.NoDataApiResponseDTO;
import com.ucd.keynote.domain.user.dto.CustomUserDetails;
import com.ucd.keynote.domain.user.dto.LoginRequestDTO;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;


public class LoginFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;
    private final ObjectMapper objectMapper = new ObjectMapper(); // JSON 처리를 위한 ObjectMapper

    // 엔드포인트를 설정하는 생성자
    public LoginFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        // 클라이언트 요청에서 JSON 본문을 추출하여 이메일과 패스워드를 읽어들임
        try {
            LoginRequestDTO loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequestDTO.class);

            // 스프링 시큐리티에서 이메일과 패스워드를 검증하기 위해 토큰에 담아야 함
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword(), null);

            // 토큰에 담은 검증을 위한 AuthenticationManager로 전달
            return authenticationManager.authenticate(authToken);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    //로그인 성공시 실행하는 메소드 (여기서 JWT를 발급하면 됨)
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) {
        try {
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

            String email = customUserDetails.getEmail();
            String username = customUserDetails.getUsername();
            Long userId = customUserDetails.getUserEntity().getUserId();

            String role = authentication.getAuthorities().iterator().next().getAuthority();

            // JWT 토큰 생성
            String token = jwtUtil.createJwt(email, username, role, userId, 60 * 60 * 19L);

            // 쿠키 생성 및 설정
            Cookie cookie = new Cookie("token", token);
            cookie.setHttpOnly(true);
            cookie.setSecure(false);
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60 * 19);
            response.addCookie(cookie);

            // 헤더에 토큰 추가
            response.addHeader("Authorization", "Bearer " + token);

            // 응답 객체 생성
            NoDataApiResponseDTO loginResponse = NoDataApiResponseDTO.builder()
                    .code(200)
                    .message("success signUp")
                    .build();


            // JSON 응답 설정
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            ObjectMapper objectMapper = new ObjectMapper();
            String jsonResponse = objectMapper.writeValueAsString(loginResponse);

            // JSON 응답 쓰기
            try (OutputStream os = response.getOutputStream()) {
                os.write(jsonResponse.getBytes(StandardCharsets.UTF_8));
                os.flush();
            }
        } catch (JsonProcessingException e) {
            e.printStackTrace(); // JSON 처리 오류 발생 시 처리
        } catch (IOException e) {
            e.printStackTrace(); // IO 오류 발생 시 처리
        }
    }


    //로그인 실패시 실행하는 메소드
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) {
        response.setStatus(401);
    }

}
