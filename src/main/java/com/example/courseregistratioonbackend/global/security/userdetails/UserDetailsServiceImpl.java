package com.example.courseregistratioonbackend.global.security.userdetails;

import com.example.courseregistratioonbackend.domain.student.entity.Student;
import com.example.courseregistratioonbackend.domain.student.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static com.example.courseregistratioonbackend.global.enums.ErrorCode.USER_NOT_FOUND;

@Service
@Slf4j(topic = "UserDetailsServiceImpl")
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

	private final StudentRepository studentRepository;

	@Override
	public UserDetails loadUserByUsername(String userNumber) {

		log.info("userNumber: {}", userNumber);

		Student studentUser = studentRepository.findByStudentNum(userNumber).orElseThrow(
			() -> new UsernameNotFoundException(USER_NOT_FOUND.getDetail())
		);

		log.info("name: {}", studentUser.getStudentNM());

		return new UserDetailsImpl(studentUser);
	}
}

