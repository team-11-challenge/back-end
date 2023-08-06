package com.example.courseregistratioonbackend.domain.registration.controller;

import com.example.courseregistratioonbackend.domain.registration.service.RegistrationService;
import com.example.courseregistratioonbackend.global.responsedto.ApiResponse;
import com.example.courseregistratioonbackend.global.utils.ResponseUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/registration/{courseId}")
public class RegistrationController {
    private final RegistrationService registrationService;

    // TODO: @AuthenticationPrincipal UserDetailsImpl userDetails 추가
    @PostMapping
    public ApiResponse<?> register(@PathVariable Long courseId) {
        return ResponseUtils.ok(registrationService.register(courseId, 1L));
    }


}
