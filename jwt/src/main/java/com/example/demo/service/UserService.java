package com.example.demo.service;

import com.example.demo.UserRepository;
import com.example.demo.domain.Role;
import com.example.demo.domain.User;
import com.example.demo.dto.CreateUserRequest;
import com.example.demo.dto.UserViewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.ValidationException;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import static java.lang.String.format;

@Service
@Transactional
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    private final UserRepository    userRepository;
    private final PasswordEncoder   passwordEncoder;

    public Long create(CreateUserRequest request) throws Exception {
        //유저 이름 찾기
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new ValidationException("Username exists!");
        }

        //패스워드 일치 여부 확인
        if (!request.getPassword().equals(request.getRePassword())) {
            throw new ValidationException("Passwords don`t match!");
        }

        // 빈 값으로 들어오면 컬렉션 생성
        if (request.getAuthorities() == null) {
            request.setAuthorities(new HashSet<>());
        }

        return userRepository.save(convertToEntity(request)).getId();
    }

    public UserViewDto getUsers(Long id) {
        return convertToDto(userRepository
                .findById(id)
                .orElseThrow(() -> new IllegalArgumentException("no such data")));
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository
                .findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException(format("User with username - %s, not found", username)));
    }


    private User convertToEntity(CreateUserRequest request) {
        return User.builder()
                .username(request.getUsername())
                .password(passwordEncoder.encode(request.getPassword()))
                .authorities(request.getAuthorities().stream().map(Role::new).collect(Collectors.toSet()))
                .build();
    }

    private UserViewDto convertToDto(User user) {
        return UserViewDto.builder()
                .id(user.getId())
                .username(user.getUsername())
                .fullName(user.getFullName())
                .build();
    }
}
