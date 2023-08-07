package com.example.courseregistratioonbackend.domain.registration.controller;

import com.example.courseregistratioonbackend.domain.registration.service.RegistrationService;
import com.example.courseregistratioonbackend.global.responsedto.ApiResponse;
import com.example.courseregistratioonbackend.global.utils.ResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/registration")
public class RegistrationController {
    private final RegistrationService registrationService;

    // TODO: @AuthenticationPrincipal UserDetailsImpl userDetails 추가
    @PostMapping("/{courseId}")
    public ApiResponse<?> register(@PathVariable Long courseId) {
        return ResponseUtils.ok(registrationService.register(courseId, 1L));
    }

    // TODO: @AuthenticationPrincipal UserDetailsImpl userDetails 추가
    @DeleteMapping("/{registrationId}")
    public ApiResponse<?> cancel(@PathVariable Long registrationId) {
        return ResponseUtils.ok(registrationService.cancel(registrationId, 1L));
    }

    // TODO: @AuthenticationPrincipal UserDetailsImpl userDetails 추가
    @GetMapping
    public ApiResponse<?> getRegistration() {
        return ResponseUtils.ok(registrationService.getRegistration(1L));
    }
}
