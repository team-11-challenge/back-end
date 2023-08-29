package com.example.courseregistratioonbackend.domain.registration.controller;

import com.example.courseregistratioonbackend.domain.registration.dto.RegistrationRequestDto;
import com.example.courseregistratioonbackend.domain.registration.event.Event;
import com.example.courseregistratioonbackend.domain.registration.service.QueueService;
import com.example.courseregistratioonbackend.domain.registration.service.RegistrationService;
import com.example.courseregistratioonbackend.domain.student.entity.Student;
import com.example.courseregistratioonbackend.global.responsedto.ApiResponse;
import com.example.courseregistratioonbackend.global.security.userdetails.UserDetailsImpl;
import com.example.courseregistratioonbackend.global.utils.ResponseUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
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
    private final QueueService queueService;

    @PostMapping("/{courseId}")
    public ApiResponse<?> register(@PathVariable Long courseId,
                                   @AuthenticationPrincipal UserDetailsImpl userDetails) throws JsonProcessingException {
        Student student = userDetails.getStudentUser();
        RegistrationRequestDto requestDto = new RegistrationRequestDto(student.getId(), courseId, student.getStudentNM());
        queueService.addQueue(Event.REGISTRATION, requestDto);
        return ResponseUtils.ok("수강신청 요청 성공");
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

