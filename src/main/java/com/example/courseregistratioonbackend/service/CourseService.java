package com.example.courseregistratioonbackend.service;

import com.example.courseregistratioonbackend.entity.Course;
import com.example.courseregistratioonbackend.parsing.ReadLineContext;
import com.example.courseregistratioonbackend.repository.CourseRepository;
import com.example.courseregistratioonbackend.repository.MajorRepository;
import com.example.courseregistratioonbackend.repository.ProfessorRepository;
import com.example.courseregistratioonbackend.repository.SubjectRepository;
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
    private final MajorRepository majorRepository;
    private final ProfessorRepository professorRepository;
    private final SubjectRepository subjectRepository;

    @Transactional
    public int insertLargeVolumeCourseData(String filename){
        List<Course> courseList;
        try{
            courseList = courseReadLineContext.readByLine(filename);
            log.info("파싱이 끝났습니다.");
            courseList.stream()
                    .parallel()
                    .forEach(course -> {
                        try{
                            majorRepository.save(course.getMajor());
                            professorRepository.save(course.getProfessor());
                            subjectRepository.save(course.getSubject());
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
