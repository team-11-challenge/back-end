package com.example.courseregistratioonbackend.global.parsing.service;

import com.example.courseregistratioonbackend.domain.course.entity.Course;
import com.example.courseregistratioonbackend.domain.course.repository.CourseRepository;
import com.example.courseregistratioonbackend.global.parsing.entity.*;
import com.example.courseregistratioonbackend.global.parsing.repository.*;
import com.example.courseregistratioonbackend.global.parsing.utils.ReadLineContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParsingService {
    private final ProfessorRepository professorRepository;
    private final ReadLineContext readLineContext;
    private final CourseRepository courseRepository;
    private final BelongRepository belongRepository;
    private final DepartmentRepository departmentRepository;
    private final CollegeRepository collegeRepository;
    private final MajorRepository majorRepository;
    private final SubjectRepository subjectRepository;


    public void insertData(String filename, int COURSEYEAR, int SEMESTER){
        List<String[]> lines;
        try{
            lines = readLineContext.readByLine(filename);
            lines.stream().forEach(line -> parse(line, COURSEYEAR, SEMESTER));
        } catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    public void parse(String[] nextLine, int COURSEYEAR, int SEMESTER) {
        // 1학기, 2학기
        // 연번0,구분1,과목코드2,분반3,교과목명4,시수5,부문6,대상학과및학년7,대상인원8,대학9,학과10,전공11,교번12,담당교수13,직종14,강의실15,비고,이러닝강좌,수강용과목,폐강여부

        // 대학
        College college = insertCollege(nextLine[9]);

        // 학과
        Department department = insertDepartment(nextLine[10]);

        // 전공
        Major major = insertMajor(nextLine[11]);

        // 소속
        Belong belong = insertBelong(college, department, major);

        Subject subject = insertSubject(nextLine[4], Long.valueOf(nextLine[2]));

        Professor professor = insertProfessor(nextLine[13]);

        insertCourse(nextLine, belong, subject, professor, COURSEYEAR, SEMESTER);
    }

    public College insertCollege(String collegeNM) {
        College college = collegeRepository.findByCollegeNM(collegeNM);
        if (college == null) {
            college = College.builder()
                    .collegeNM(collegeNM)
                    .collegeCD(0L)
                    .build();
            try {
                collegeRepository.save(college);
            } catch(Exception e) {
                log.error(e.getMessage());
                throw new RuntimeException(e);
            }
        }
        return college;
    }

    public Department insertDepartment(String departNM) {
        Department department = null;
        if (!departNM.equals("")) {
            department = departmentRepository.findByDepartNM(departNM);
            if (department == null) {
                department = Department.builder()
                        .departNM(departNM)
                        .departCD(0L)
                        .build();
                try {
                    departmentRepository.save(department);
                } catch(Exception e) {
                    log.error(e.getMessage());
                    throw new RuntimeException(e);
                }
            }
        }
        return department;
    }

    public Major insertMajor(String majorNM) {
        Major major = null;
        if (!majorNM.equals("")) {
            major = majorRepository.findByMajorNM(majorNM);
            if (major == null) {
                major = Major.builder()
                        .majorNM(majorNM)
                        .majorCD(0L)
                        .build();
                try {
                    majorRepository.save(major);
                } catch(Exception e) {
                    log.error(e.getMessage());
                    throw new RuntimeException(e);
                }
            }
        }
        return major;
    }

    public Belong insertBelong(College college, Department department, Major major) {
        Belong belong = belongRepository.findByCollegeAndDepartmentAndMajor(college, Optional.ofNullable(department), Optional.ofNullable(major));
        if (belong == null) {
            belong = Belong.builder()
                    .college(college)
                    .department(department)
                    .major(major)
                    .build();
            try {
                belongRepository.save(belong);
            } catch(Exception e) {
                log.error(e.getMessage());
                throw new RuntimeException(e);
            }
        }
        return belong;
    }

    public Subject insertSubject(String subjectNM, Long subjectCD) {
        Subject subject = subjectRepository.findBySubjectCD(subjectCD).orElseThrow();
        if (subject == null) {
            subject = Subject.builder()
                    .subjectNM(subjectNM)
                    .subjectCD(subjectCD)
                    .build();
            try {
                subjectRepository.save(subject);
            } catch(Exception e) {
                log.error(e.getMessage());
                throw new RuntimeException(e);
            }
        }
        return subject;
    }

    public Professor insertProfessor(String name) {
        Professor professor = professorRepository.findByProfessorNM(name);
        if(professor == null){
            professor = Professor.builder()
                    .professorNM(name)
                    .build();
            try {
                professorRepository.save(professor);
            } catch(Exception e) {
                log.error(e.getMessage());
                throw new RuntimeException(e);
            }
        }

        return professor;
    }

    public void insertCourse(String[] nextLine, Belong belong, Subject subject, Professor professor, int COURSEYEAR, int SEMESTER) {
        Course course = courseRepository.findByCourseYearAndSemesterAndSubjectAndDivision(COURSEYEAR, SEMESTER, subject, Integer.parseInt(nextLine[3]));
        if (course == null) {
            course = Course.builder()
                    .sort(nextLine[1])
                    .division(Integer.parseInt(nextLine[3]))
                    .credit(Integer.parseInt(nextLine[5].substring(0, 1)))
                    .timetable(pretreatment(nextLine[15]))
                    .limitation(Long.valueOf(nextLine[8]))
                    .courseYear(COURSEYEAR)
                    .semester(SEMESTER)
                    .belong(belong)
                    .subject(subject)
                    .professor(professor)
                    .build();
            try {
                courseRepository.save(course);
            } catch(Exception e) {
                log.error(e.getMessage());
                throw new RuntimeException(e);
            }
        }
    }

    public String pretreatment(String timetable){
        timetable = timetable.replaceAll("[A-Za-z]", ""); // 알파벳 제거
        String ans = "";
        LinkedList<String> fList = new LinkedList<>();
        String[] list = timetable.split(",");
        for(int i = 0; i < list.length; i++){
            // 공백 제거
            list[i].trim();
            // 장소 제거
            if(list[i].contains("(")){
                list[i] = list[i].substring(0, list[i].indexOf("("));
            }
            // 여러글자면 나눠주기
            if(list[i].length() > 1 && !isInteger(list[i])){
                fList.add(list[i].substring(0,1));
                fList.add(list[i].substring(1));
            }else if(list[i].length() <= 1 || isInteger(list[i])){ // 공백인 경우도 있음
                fList.add(list[i]);
            }
        }

        ans += fList.get(0);

        for(int i = 1; i < fList.size(); i++){
            if(isInteger(fList.get(i))){
                ans += " " + fList.get(i);
            }else{
                ans += "," + fList.get(i);
            }
        }

        return ans;
    }

    public boolean isInteger(String strValue){
        try{
            Integer.parseInt(strValue);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }
}
