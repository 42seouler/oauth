package com.example.demo.configuration.security;

import com.example.demo.UserRepository;
import com.example.demo.domain.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;

import static java.util.Optional.of;
import static java.util.Optional.ofNullable;


@Component
@RequiredArgsConstructor
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenUtil jwtTokenUtil;
    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        // The HTTP Cache-Control header field name.
        final String header = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (ObjectUtils.isEmpty(header) || !header.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        // 토큰을 얻고 유효성 검사를 함
        final String token = header.split(" ")[1].trim();
        if (!jwtTokenUtil.validate(token)) {
            filterChain.doFilter(request, response);
            return;
        }

        // repo에서 유저를 찾아 userDetails(인터페이스타입)에 담아 spring security context에 전달 할 준비함
        UserDetails userDetails = userRepository
                .findByUsername(jwtTokenUtil.getUsername(token))
                .orElse(null);

        // 2번째 인자 Null인 경우 권한없음으로 설정 AuthorityUtils.NO_AUTHORITIES는 Empty list로 설정됨
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails,
                null,
                ofNullable(userDetails).map(UserDetails::getAuthorities).orElse(null));

        //웹 인증 요청과 관련하여 http 세부정보 셋팅
        authenticationToken
                .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        filterChain.doFilter(request, response);
    }
}
