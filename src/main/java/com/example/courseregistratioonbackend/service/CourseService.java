package com.example.courseregistratioonbackend.service;

import com.example.courseregistratioonbackend.entity.*;
import com.example.courseregistratioonbackend.parsing.ReadLineContext;
import com.example.courseregistratioonbackend.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class CourseService {
    private final ReadLineContext<Course> courseReadLineContext;
    private final CourseRepository courseRepository;
    private final BelongRepository belongRepository;
    private final DepartmentRepository departmentRepository;
    private final CollegeRepository collegeRepository;
    private final MajorRepository majorRepository;
    private final ProfessorRepository professorRepository;
    private final SubjectRepository subjectRepository;

    public int insertLargeVolumeCourseData(String filename){
        List<Course> courseList;
        try{
            courseList = courseReadLineContext.readByLine(filename);
            log.info("파싱이 끝났습니다.");
            courseList.stream()
                    .parallel()
                    .forEach(course -> {
                        try {
                            College college = collegeRepository.findByCollegeNM(course.getBelong().getCollege().getCollegeNM());
                            if(college != null){
                                course.getBelong().setCollege(college);
                            }else{
                                collegeRepository.save(course.getBelong().getCollege());
                            }

                            if (course.getBelong().getDepartment() != null) {
                                Department department = departmentRepository.findByDepartNM(course.getBelong().getDepartment().getDepartNM());
                                if (department != null) {
                                    course.getBelong().setDepartment(department);
                                } else {
                                    departmentRepository.save(course.getBelong().getDepartment());
                                }
                            }

                            if (course.getBelong().getMajor() != null) {
                                Major major = majorRepository.findByMajorNM(course.getBelong().getMajor().getMajorNM());
                                if (major != null) {
                                    course.getBelong().setMajor(major);
                                } else {
                                    majorRepository.save(course.getBelong().getMajor());
                                }
                            }

                            Belong belong = belongRepository.findByCollegeAndDepartmentAndMajor(
                                    course.getBelong().getCollege(),
                                    course.getBelong().getDepartment(),
                                    course.getBelong().getMajor());
                            if (belong != null) {
                                course.setBelong(belong);
                            } else {
                                belongRepository.save(course.getBelong());
                            }
                            professorRepository.save(course.getProfessor());

                            if(course.getSubject() != null){
                                Subject subject = subjectRepository.findBySubjectCD(course.getSubject().getSubjectCD());
                                if(subject != null){
                                    course.setSubject(subject);
                                }else{
                                    subjectRepository.save(course.getSubject());
                                }
                            }
                            courseRepository.save(course);

                        } catch(Exception e){
                            log.error(course.getSubject().getSubject() + ": 레코드에 문제가 있습니다.");
                            log.error(e.getMessage());
                            throw new RuntimeException(e);
                        }
                    });
        } catch (IOException e){
            throw new RuntimeException(e);
        }
        if(courseList.isEmpty()){
            return 0;
        }else{
            return courseList.size();
        }
    }
}
