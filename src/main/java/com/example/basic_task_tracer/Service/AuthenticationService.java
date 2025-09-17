package com.example.basic_task_tracer.Service;


import com.example.basic_task_tracer.Dto.UserDto;
import com.example.basic_task_tracer.Entity.User;
import com.example.basic_task_tracer.Repositories.UserRepository;
import com.example.basic_task_tracer.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public String registerUser(UserDto user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("user with this name already exist");
        }



        User users = User.builder()
                .username(user.getUsername())
                .password(passwordEncoder.encode(user.getPassword()))
                .build();

        userRepository.saveAndFlush(users);


        return jwtUtil.generateToken(user.getUsername());
    }
}