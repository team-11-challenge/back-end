package com.example.courseregistratioonbackend.domain.student.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class LoginRequestDto {

	@NotBlank(message = "학번은 공백이 불가능합니다.")
	private String studentNum;

	@NotBlank(message = "비밀번호는 공백이 불가능합니다.")
	private String password;

}
