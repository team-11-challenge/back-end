package com.example.courseregistratioonbackend.domain.basket.service;

import com.example.courseregistratioonbackend.domain.basket.dto.CourseFromBasketResponseDto;
import com.example.courseregistratioonbackend.domain.basket.entity.Basket;
import com.example.courseregistratioonbackend.domain.basket.exception.AlreadyExistedInBasketException;
import com.example.courseregistratioonbackend.domain.basket.exception.CourseNotFoundInBasketException;
import com.example.courseregistratioonbackend.domain.basket.exception.NotMatchedOwnerException;
import com.example.courseregistratioonbackend.domain.basket.repository.BasketRepository;
import com.example.courseregistratioonbackend.domain.course.entity.Course;
import com.example.courseregistratioonbackend.domain.course.exception.CourseNotFoundException;
import com.example.courseregistratioonbackend.domain.course.repository.CourseRepository;
import com.example.courseregistratioonbackend.domain.student.entity.Student;
import com.example.courseregistratioonbackend.domain.student.execption.StudentNotFoundException;
import com.example.courseregistratioonbackend.domain.student.repository.StudentRepository;
import com.example.courseregistratioonbackend.global.enums.SuccessCode;
import com.example.courseregistratioonbackend.global.parsing.entity.Belong;
import com.example.courseregistratioonbackend.global.parsing.entity.Professor;
import com.example.courseregistratioonbackend.global.parsing.entity.Subject;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static com.example.courseregistratioonbackend.global.enums.ErrorCode.*;
import static com.example.courseregistratioonbackend.global.enums.SuccessCode.ADD_COURSE_TO_BASKET_SUCCESS;
import static com.example.courseregistratioonbackend.global.enums.SuccessCode.DELETE_COURSE_FROM_BASKET_SUCCESS;

@Slf4j(topic = "BasketService")
@Service
@RequiredArgsConstructor
public class BasketService {

	private final BasketRepository basketRepository;
	private final StudentRepository studentRepository;
	private final CourseRepository courseRepository;

	// 장바구니 조회 메서드
	public List<CourseFromBasketResponseDto> getCourseListFromBasket(Long studentId) {

		return basketRepository.findByStudentId(studentId).stream()
			.map(
				// 사용자에 해당하는 장바구니 내역을 하나씩 찾아옴
				basketItem -> {

					// 각 내역에서 강의를 찾아옴.
					Long courseId = basketItem.getCourse().getId();

					Course course = courseRepository.findById(courseId).orElseThrow(
						() -> new CourseNotFoundException(COURSE_NOT_FOUND)
					);

					// 강의에 대한 다른 테이블항목
					Belong belong = course.getBelong();
					Subject subject = course.getSubject();
					Professor professor = course.getProfessor();

					// null 값이 존재할 수 있는 항목 처리
					String collegeName = belong.getCollege() != null ? belong.getCollege().getCollegeNM() : null;
					String departmentName = belong.getDepartment() != null ? belong.getDepartment().getDepartNM() : null;
					String majorName = belong.getMajor() != null ? belong.getMajor().getMajorNM() : null;

					// 찾아온 강의와 다른 테이블 항목을 통해 빌더로 ResponseDto 생성
					return CourseFromBasketResponseDto.builder()
						.basketId(basketItem.getId())
						.courseId(courseId)
						.collegeName(collegeName)
						.departmentName(departmentName)
						.majorName(majorName)
						.sort(course.getSort())
						.subjectCd(subject.getSubjectCD())
						.division(course.getDivision())
						.subjectName(subject.getSubjectNM())
						.credit(course.getCredit())
						.professorName(professor.getProfessorNM())
						.timetable(course.getTimetable())
						.limitation(course.getLimitation())
						.numberOfBasket(course.getBasket())
						.build();
				}

			).toList(); // ResponseDto 를 리스트로 반환
	}

	// 장바구니 수강과목 추가 메서드
	@Transactional
	public SuccessCode addCourseToBasket(Long courseId, Long studentId) {

		// 중복 확인
		if (isExistedInBasket(courseId, studentId)) {
			throw new AlreadyExistedInBasketException(ALREADY_BASKET_EXISTED);
		}

		Student student = studentRepository.findStudentByIdAndLock(studentId).orElseThrow(
			() -> new StudentNotFoundException(STUDENT_NOT_FOUND)
		);

		Course course = courseRepository.findCourseByIdAndLock(courseId).orElseThrow(
			() -> new CourseNotFoundException(COURSE_NOT_FOUND)
		);

		// 장바구니에 내역 저장
		Basket basket = Basket.builder()
			.course(course)
			.student(student)
			.build();

		basketRepository.save(basket);

		// 장바구니 인원 추가
		course.addToBasketNumber();

		log.info("studentId: {}, courseId: {} 장바구니 추가 완료", studentId, courseId);
		return ADD_COURSE_TO_BASKET_SUCCESS;
	}

	private boolean isExistedInBasket(Long courseId, Long studentId) {
		return basketRepository.findByCourseIdAndStudentId(courseId, studentId).isPresent();
	}

	// 장바구니 수강과목 삭제 메서드
	@Transactional
	public SuccessCode deleteCourseFromBasket(Long basketId, Long studentId) {

		Basket basket = basketRepository.findById(basketId).orElseThrow(
			() -> new CourseNotFoundInBasketException(BASKET_DATA_NOT_FOUND)
		);

		Long studentIdFromBasket = basket.getStudent().getId();

		if (!Objects.equals(studentIdFromBasket, studentId)) {
			throw new NotMatchedOwnerException(NO_AUTHORITY_TO_BASKET);
		}

		Long courseId = basket.getCourse().getId();

		Course course = courseRepository.findById(courseId).orElseThrow(
			() -> new CourseNotFoundException(COURSE_NOT_FOUND)
		);

		// 장바구니 내역 제거
		basketRepository.delete(basket);

		// 장바구니 인원 제거
		course.removeFromBasketNumber();

		log.info("studentId: {}, courseId: {} 장바구니 제거 완료", studentId, courseId);
		return DELETE_COURSE_FROM_BASKET_SUCCESS;
	}

}
