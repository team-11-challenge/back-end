package com.example.courseregistratioonbackend.global.security.userdetails;

import com.example.courseregistratioonbackend.domain.student.entity.Student;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@RequiredArgsConstructor
@Getter
public class UserDetailsImpl implements UserDetails {

	private final Student studentUser;

	@Override
	public String getPassword() {
		return studentUser.getPassword();
	}

	@Override
	public String getUsername() {
		return studentUser.getStudentNum();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// Collection<GrantedAuthority> authorities = new ArrayList<>();
		// return authorities;
		// role 구분 없음.
		return null;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}
}
