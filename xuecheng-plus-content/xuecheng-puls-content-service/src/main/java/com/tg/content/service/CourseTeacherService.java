package com.tg.content.service;

import com.tg.content.model.dto.CourseTeacherDto;
import com.tg.content.model.po.CourseTeacher;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface CourseTeacherService {

    List<CourseTeacherDto> getCourseTeacher(Long courseId);

    CourseTeacherDto addCourseTeacher(CourseTeacher courseTeacher);

    CourseTeacherDto updateCourseTeacher(CourseTeacher courseTeacher);

    void deleteCourseTeacher( Long courseId , Long id);
}
