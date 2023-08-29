package com.example.courseregistratioonbackend.domain.registration.controller;

import com.example.courseregistratioonbackend.domain.registration.facade.RedisLockRegistrationFacade;
import com.example.courseregistratioonbackend.domain.registration.service.RegistrationService;
import com.example.courseregistratioonbackend.global.responsedto.ApiResponse;
import com.example.courseregistratioonbackend.global.security.userdetails.UserDetailsImpl;
import com.example.courseregistratioonbackend.global.utils.ResponseUtils;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@Tag(name = "수강신청 관련 API", description = "수강신청 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/registration")
public class RegistrationController {
    private final RegistrationService registrationService;
    private final RedisLockRegistrationFacade redisLockRegistrationFacade;

    @PostMapping("/{courseId}")
    public ApiResponse<?> register(@PathVariable Long courseId,
                                   @AuthenticationPrincipal UserDetailsImpl userDetails) throws InterruptedException {
        return ResponseUtils.ok(redisLockRegistrationFacade.registerByRedisLock(courseId, userDetails.getStudentUser().getId()));
    }

    @DeleteMapping("/{registrationId}")
    public ApiResponse<?> cancel(@PathVariable Long registrationId,
                                 @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseUtils.ok(registrationService.cancel(registrationId, userDetails.getStudentUser().getId()));
    }

    @GetMapping
    public ApiResponse<?> getRegistration(@AuthenticationPrincipal UserDetailsImpl userDetails) {
        return ResponseUtils.ok(registrationService.getRegistration(userDetails.getStudentUser().getId()));
    }
}

