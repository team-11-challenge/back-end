package com.example.courseregistratioonbackend.domain.basket.service;

import static com.example.courseregistratioonbackend.global.enums.SuccessCode.*;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.courseregistratioonbackend.domain.basket.dto.CourseFromBasketResponseDto;
import com.example.courseregistratioonbackend.domain.basket.entity.Basket;
import com.example.courseregistratioonbackend.domain.basket.exception.AlreadyExistedInBasketException;
import com.example.courseregistratioonbackend.domain.basket.exception.CourseNotFoundInBasketException;
import com.example.courseregistratioonbackend.domain.basket.exception.NotMatchedOwnerException;
import com.example.courseregistratioonbackend.domain.basket.repository.BasketRepository;
import com.example.courseregistratioonbackend.domain.course.entity.Course;
import com.example.courseregistratioonbackend.domain.course.repository.CourseRepository;
import com.example.courseregistratioonbackend.domain.student.entity.Student;
import com.example.courseregistratioonbackend.domain.student.repository.StudentRepository;
import com.example.courseregistratioonbackend.global.enums.SuccessCode;
import com.example.courseregistratioonbackend.global.parsing.entity.Belong;
import com.example.courseregistratioonbackend.global.parsing.entity.College;
import com.example.courseregistratioonbackend.global.parsing.entity.Department;
import com.example.courseregistratioonbackend.global.parsing.entity.Major;
import com.example.courseregistratioonbackend.global.parsing.entity.Professor;
import com.example.courseregistratioonbackend.global.parsing.entity.Subject;

public class BasketServiceTest {

	@Mock
	private BasketRepository basketRepository;

	@Mock
	private CourseRepository courseRepository;

	@Mock
	private StudentRepository studentRepository;

	@InjectMocks
	private BasketService basketService;

	private List<Student> students;
	private List<Course> courses;
	private List<Basket> baskets;
	private List<Professor> professors;

	private List<Subject> subjects;
	private List<Belong> belongs;
	private List<College> colleges;
	private List<Department> departments;
	private List<Major> majors;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.openMocks(this);

		// list 생성
		this.students = new ArrayList<>();
		this.courses = new ArrayList<>();
		this.baskets = new ArrayList<>();
		this.professors = new ArrayList<>();

		this.subjects = new ArrayList<>();
		this.belongs = new ArrayList<>();
		this.colleges = new ArrayList<>();
		this.departments = new ArrayList<>();
		this.majors = new ArrayList<>();

		// 학생 정보
		for (int i = 1; i <= 3; i++) {
			Long studentId = (long)i;
			String studentNum = "111111" + i;
			String studentNM = "홍길동" + i;
			String password = "password";
			Integer possibleCredits = 18;

			Student student = Student.builder()
				.id(studentId)
				.studentNum(studentNum)
				.studentNM(studentNM)
				.password(password)
				.possibleCredits(possibleCredits)
				.build();

			students.add(student);
		}

		// Course 에 들어갈 소속, 과목, 교수
		for (int i = 1; i <= 3; i++) {

			// college 생성
			College college = College.builder()
				.id((long)i)
				.collegeCD(1000000 + (long)i)
				.collegeNM("대학" + i)
				.build();

			colleges.add(college);

			// department 생성
			Department department = Department.builder()
				.id((long)i)
				.departCD(2000000 + (long)i)
				.departNM("학과" + i)
				.build();

			departments.add(department);

			// major 생성
			Major major = Major.builder()
				.id((long)i)
				.majorCD(3000000 + (long)i)
				.majorNM("전공" + i)
				.build();

			majors.add(major);

			// belong 생성
			Belong belong = Belong.builder()
				.id((long)i)
				.department(department)
				.college(college)
				.major(major)
				.build();

			belongs.add(belong);

			// Subject 생성
			Subject subject = Subject.builder()
				.id((long)i)
				.subjectCD(4000000 + (long)i)
				.subjectNM("과목" + i)
				.build();

			subjects.add(subject);

			// Professor 생성
			Professor professor = Professor.builder()
				.id((long)i)
				.professorNM("교수" + i)
				.build();

			professors.add(professor);

			// 생성할 Course 데이터

			String sort = "구분" + i;
			int division = i;
			int credit = i;
			String timetable = "월 1 2 3";
			Long limitation = 40L + i;
			int courseYear = 2019 + i;
			int semester = 2;

			Course course = Course.builder()
				.id((long)i)
				.sort(sort)
				.division(division)
				.credit(credit)
				.timetable(timetable)
				.limitation(limitation)
				.basket(0L)
				.current(0L)
				.courseYear(courseYear)
				.semester(semester)
				.belong(belong)
				.subject(subject)
				.professor(professor)
				.build();

			courses.add(course);
		}

	}

	@Test
	@DisplayName("장바구니 수강과목 조회 테스트")
	void getCourseFromBasketTest() {
		// given
		Long studentId = 1L;
		Long courseId = 1L;

		Student student = students.get(0);

		Course course = courses.get(0);

		Basket basket = Basket.builder()
			.student(student)
			.course(course)
			.build();

		baskets.add(basket);

		// when
		when(basketRepository.findByStudentId(studentId)).thenReturn(baskets);
		when(courseRepository.findById(courseId)).thenReturn(Optional.ofNullable(course));
		List<CourseFromBasketResponseDto> result = basketService.getCourseListFromBasket(studentId);

		// then
		assertThat(result).isNotEmpty();
		assertThat(result).hasSize(1);
		assertThat(result).hasSize(baskets.size());
	}

	@Test
	@DisplayName("장바구니 여러개 수강과목 조회 테스트")
	void addCoursesFromBasketTest() {
		// given
		Long studentId = 1L;
		Long courseId1 = 1L;
		Long courseId2 = 2L;
		Long courseId3 = 3L;

		Student student = students.get(0);

		Course course1 = courses.get(0);
		Course course2 = courses.get(1);
		Course course3 = courses.get(2);

		Basket basket1 = Basket.builder()
			.student(student)
			.course(course1)
			.build();

		Basket basket2 = Basket.builder()
			.student(student)
			.course(course2)
			.build();

		Basket basket3 = Basket.builder()
			.student(student)
			.course(course3)
			.build();

		baskets.add(basket1);
		baskets.add(basket2);
		baskets.add(basket3);

		// when
		when(basketRepository.findByStudentId(studentId)).thenReturn(baskets);
		when(courseRepository.findById(courseId1)).thenReturn(Optional.ofNullable(course1));
		when(courseRepository.findById(courseId2)).thenReturn(Optional.ofNullable(course2));
		when(courseRepository.findById(courseId3)).thenReturn(Optional.ofNullable(course3));
		List<CourseFromBasketResponseDto> result = basketService.getCourseListFromBasket(studentId);

		// then
		assertThat(result).isNotEmpty();
		assertThat(result).hasSize(3);
		assertThat(result).hasSize(baskets.size());
	}

	@Test
	@DisplayName("장바구니 강의 중복 테스트")
	void checkDuplicatedInBasketTest() {

		// given
		Long studentId = 1L;
		Long courseId = 1L;

		Student student = students.get(0);

		Course course = courses.get(0);

		Basket basket = Basket.builder()
			.student(student)
			.course(course)
			.build();

		baskets.add(basket);

		// when
		// 해당 강의가 이미 존재함.
		when(basketRepository.findByCourseIdAndStudentId(courseId, studentId)).thenReturn(
			Optional.ofNullable(baskets.get(0)));

		// then
		assertThatThrownBy(() -> basketService.addCourseToBasket(courseId, studentId))
			.isInstanceOf(AlreadyExistedInBasketException.class)
			.hasMessageContaining("이미 장바구니에 담은 과목입니다.");

	}

	@Test
	@DisplayName("장바구니 강의 추가 테스트")
	void addCourseToBasketTest() {
		// given
		Long studentId = 1L;
		Long courseId1 = 1L;
		Long courseId2 = 2L;
		Long courseId3 = 3L;

		Student student = students.get(0);

		Course course1 = courses.get(0);
		Course course2 = courses.get(1);
		Course course3 = courses.get(2);

		Basket basket1 = Basket.builder()
			.student(student)
			.course(course1)
			.build();

		Basket basket2 = Basket.builder()
			.student(student)
			.course(course2)
			.build();

		Basket basket3 = Basket.builder()
			.student(student)
			.course(course3)
			.build();

		baskets.add(basket1);
		baskets.add(basket2);
		baskets.add(basket3);

		// when
		when(basketRepository.findByCourseIdAndStudentId(courseId1, studentId)).thenReturn(
			Optional.ofNullable(basket1));
		when(basketRepository.findByCourseIdAndStudentId(courseId2, studentId)).thenReturn(
			Optional.ofNullable(basket2));
		// 장바구니 강의 3번은 아직 추가전이라 가정(중복 검증)
		when(basketRepository.findByCourseIdAndStudentId(courseId3, studentId)).thenReturn(
			Optional.empty());
		when(studentRepository.findStudentByIdAndLock(studentId)).thenReturn(Optional.of(student));
		when(basketRepository.findByStudentId(studentId)).thenReturn(baskets);
		when(courseRepository.findCourseByIdAndLock(courseId1)).thenReturn(Optional.ofNullable(course1));
		when(courseRepository.findCourseByIdAndLock(courseId2)).thenReturn(Optional.ofNullable(course2));
		when(courseRepository.findCourseByIdAndLock(courseId3)).thenReturn(Optional.ofNullable(course3));
		when(courseRepository.findById(courseId1)).thenReturn(Optional.ofNullable(course1));
		when(courseRepository.findById(courseId2)).thenReturn(Optional.ofNullable(course2));
		when(courseRepository.findById(courseId3)).thenReturn(Optional.ofNullable(course3));

		SuccessCode toBeSuccessCode = basketService.addCourseToBasket(courseId3, studentId);

		List<CourseFromBasketResponseDto> result = basketService.getCourseListFromBasket(studentId);

		// then
		assertThat(toBeSuccessCode).isEqualTo(ADD_COURSE_TO_BASKET_SUCCESS);

		assertThat(result.size()).isEqualTo(3);
		assertThat(result.size()).isEqualTo(baskets.size());

		// 1번 실행 검증
		verify(basketRepository, times(1)).findByCourseIdAndStudentId(courseId3, studentId);

	}

	@Test
	@DisplayName("수강과목 장바구니 인원수 증가 테스트")
	void checkBasketNumberTest() {
		// given
		Long studentId = 1L;

		Long courseId = 1L;

		Student student = students.get(0);

		Course course = courses.get(0);

		Basket basket = Basket.builder()
			.student(student)
			.course(course)
			.build();

		baskets.add(basket);

		// when
		// 중복이 아니라고 가정
		when(basketRepository.findByCourseIdAndStudentId(courseId, studentId)).thenReturn(
			Optional.empty());
		when(basketRepository.findByStudentId(studentId)).thenReturn(baskets);
		when(courseRepository.findCourseByIdAndLock(courseId)).thenReturn(Optional.ofNullable(course));
		when(studentRepository.findStudentByIdAndLock(studentId)).thenReturn(Optional.ofNullable(student));

		SuccessCode toBeSuccessCode = basketService.addCourseToBasket(courseId, studentId);

		Long result = course.getBasket();

		// then
		assertThat(toBeSuccessCode).isEqualTo(ADD_COURSE_TO_BASKET_SUCCESS);
		assertThat(result).isEqualTo(1);

		// 검증
		verify(basketRepository, times(1)).findByCourseIdAndStudentId(courseId, studentId);

	}

	@Test
	@DisplayName("장바구니에 삭제할 대상이 없으면 오류 발생 테스트")
	void checkNotFoundFromBasketTest() {
		// given
		Long studentId = 1L;

		Course course = courses.get(0);
		Student student = students.get(0);

		Basket basket = Basket.builder()
			.course(course)
			.student(student)
			.build();

		baskets.add(basket);

		Long basketId = basket.getId();

		// when
		// 해당 강의가 없다면 삭제 불가
		when(basketRepository.findById(basketId)).thenReturn(
			Optional.empty());

		// then
		assertThatThrownBy(() -> basketService.deleteCourseFromBasket(basketId, studentId))
			.isInstanceOf(CourseNotFoundInBasketException.class)
			.hasMessageContaining("해당 장바구니의 내역을 찾을 수 없습니다.");
	}

	@Test
	@DisplayName("장바구니의 소유자가 아니면 삭제 불가 테스트")
	void checkNotMatchedOwnerTest() {
		// given
		Long studentId = 1L; // 소유자
		Long studentId2 = 2L; // 미소유자
		Long courseId = 1L;

		Course course = courses.get(0);
		Student student = students.get(0);

		Basket basket = Basket.builder()
			.course(course)
			.student(student)
			.build();

		baskets.add(basket);

		Long basketId = basket.getId();

		// when
		when(basketRepository.findById(basketId)).thenReturn(
			Optional.of(basket));
		when(courseRepository.findById(courseId)).thenReturn(
			Optional.of(course));

		// then
		assertThatThrownBy(() -> basketService.deleteCourseFromBasket(basketId, studentId2))
			.isInstanceOf(NotMatchedOwnerException.class)
			.hasMessageContaining("해당 수강과목을 장바구니에 담은 사용자가 아닙니다.");
	}

	@Test
	@DisplayName("장바구니 수강과목 삭제 테스트")
	void deleteCourseFromBasketTest() {
		// given
		Long studentId = 1L;
		Long courseId1 = 1L;
		Long courseId2 = 2L;
		Long courseId3 = 3L;

		Student student = students.get(0);

		Course course1 = courses.get(0);
		Course course2 = courses.get(1);
		Course course3 = courses.get(2);

		Basket basket1 = Basket.builder()
			.student(student)
			.course(course1)
			.build();

		Basket basket2 = Basket.builder()
			.student(student)
			.course(course2)
			.build();

		Basket basket3 = Basket.builder()
			.student(student)
			.course(course2)
			.build();

		baskets.add(basket1);
		baskets.add(basket2);
		baskets.add(basket3);

		// when
		when(studentRepository.findById(studentId)).thenReturn(Optional.of(student));
		when(basketRepository.findByStudentId(studentId)).thenReturn(baskets);
		when(basketRepository.findById(basket1.getId())).thenReturn(
			Optional.ofNullable(basket1));
		when(basketRepository.findById(basket2.getId())).thenReturn(
			Optional.ofNullable(basket2));
		when(basketRepository.findById(basket3.getId())).thenReturn(
			Optional.ofNullable(basket3));

		when(courseRepository.findById(courseId1)).thenReturn(Optional.ofNullable(course1));
		when(courseRepository.findById(courseId2)).thenReturn(Optional.ofNullable(course2));
		when(courseRepository.findById(courseId3)).thenReturn(Optional.ofNullable(course3));

		SuccessCode toBeSuccessCode = basketService.deleteCourseFromBasket(basket1.getId(), studentId);
		baskets.remove(basket3);

		List<CourseFromBasketResponseDto> result = basketService.getCourseListFromBasket(studentId);

		// then
		// 성공 후 반환 검증
		assertThat(toBeSuccessCode).isEqualTo(DELETE_COURSE_FROM_BASKET_SUCCESS);

		// 삭제 후 갯수 검증 ( 3 -> 2 )
		assertThat(result).hasSize(2);

	}

	@Test
	@DisplayName("수강과목 장바구니 인원수 감소 테스트")
	void checkBasketNumberByRemovalTest() {
		// given
		Long studentId = 1L;

		Long courseId = 1L;

		Student student = students.get(0);

		Course course = courses.get(0);

		Basket basket = Basket.builder()
			.student(student)
			.course(course)
			.build();

		baskets.add(basket);

		// when
		// 중복이 아니라고 가정
		when(basketRepository.findById(basket.getId())).thenReturn(
			Optional.ofNullable(basket));
		when(basketRepository.findByStudentId(studentId)).thenReturn(baskets);
		when(courseRepository.findById(courseId)).thenReturn(Optional.ofNullable(course));
		when(studentRepository.findById(studentId)).thenReturn(Optional.ofNullable(student));

		SuccessCode toBeSuccessCode = basketService.deleteCourseFromBasket(basket.getId(), studentId);
		baskets.remove(basket);

		Long result = course.getBasket();

		// then
		assertThat(toBeSuccessCode).isEqualTo(DELETE_COURSE_FROM_BASKET_SUCCESS);
		assertThat(result).isEqualTo(-1); // 감소만 확인, 실제로는 강의가 없다면 삭제할 수 없기 때문에 불가능한 값임.

		// 검증
		verify(basketRepository, times(1)).findById(basket.getId());

	}

}