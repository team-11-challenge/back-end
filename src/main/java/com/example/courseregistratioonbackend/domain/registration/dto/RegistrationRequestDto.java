package com.example.courseregistratioonbackend.domain.registration.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor
public class RegistrationRequestDto {
    private long studentId;
    private long courseId;
}
