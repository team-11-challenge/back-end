package com.example.courseregistratioonbackend.domain.basket.controller;

import com.example.courseregistratioonbackend.domain.basket.dto.CourseFromBasketResponseDto;
import com.example.courseregistratioonbackend.domain.basket.service.BasketService;
import com.example.courseregistratioonbackend.global.enums.SuccessCode;
import com.example.courseregistratioonbackend.global.responsedto.ApiResponse;
import com.example.courseregistratioonbackend.global.security.userdetails.UserDetailsImpl;
import com.example.courseregistratioonbackend.global.utils.ResponseUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "예비수강신청(장바구니) 관련 API", description = "예비수강신청(장바구니) 관련 API")
@Slf4j(topic = "BasketController")
@RequestMapping("/api/basket")
@RestController
@RequiredArgsConstructor
public class BasketController {

	private final BasketService basketService;

	@Operation(summary = "장바구니 목록 조회", description = "학생이 신청한 장바구니 목록 조회")
	@Parameter(name = "userDetails", description = "조회할 학생 정보 ")
	@GetMapping()
	public ApiResponse<?> getCourseListFromBasket(@AuthenticationPrincipal UserDetailsImpl userDetails) {

		Long studentId = userDetails.getStudentUser().getId();

		List<CourseFromBasketResponseDto> courseListFromBasket = basketService.getCourseListFromBasket(studentId);

		return ResponseUtils.ok(courseListFromBasket);
	}

	@Operation(summary = "장바구니 신청(예비 수강 신청)", description = "해당 학생 장바구니 신청")
	@Parameter(name = "courseId", description = "장바구니에 신청할 강의 ID ")
	@PostMapping("/{courseId}")
	public ApiResponse<?> addCourseToBasket(@PathVariable("courseId") Long courseId,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {

		Long studentId = userDetails.getStudentUser().getId();

		SuccessCode successCode = basketService.addCourseToBasket(courseId, studentId);

		return ResponseUtils.ok(successCode);
	}

	@Operation(summary = "장바구니 취소(예비 수강 신청 취소)", description = "해당 학생 장바구니 신청 취소")
	@Parameter(name = "courseId", description = "장바구니에 신청할 강의 ID ")
	@DeleteMapping("/{basketId}")
	public ApiResponse<?> deleteCourseFromBasket(@PathVariable("basketId") Long basketId,
		@AuthenticationPrincipal UserDetailsImpl userDetails) {

		Long studentId = userDetails.getStudentUser().getId();

		SuccessCode successCode = basketService.deleteCourseFromBasket(basketId, studentId);

		return ResponseUtils.ok(successCode);
	}

}
