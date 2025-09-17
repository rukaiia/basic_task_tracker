package com.example.basic_task_tracer.Controller;

import com.example.basic_task_tracer.Dto.UserDto;
import com.example.basic_task_tracer.Service.AuthenticationService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Transactional
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthenticationController {
    AuthenticationService authenticationService;

    @PostMapping("/api/register")
    public ResponseEntity<String> register(@RequestBody UserDto userDto) {
        String token = authenticationService.registerUser(userDto);
        return ResponseEntity.ok(token);
    }

}
