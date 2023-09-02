package com.example.courseregistratioonbackend.domain.registration.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Event {
    REGISTRATION("수강신청");
    private String name;
}
