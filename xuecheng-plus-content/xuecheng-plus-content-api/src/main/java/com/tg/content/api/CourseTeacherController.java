package com.tg.content.api;

import com.tg.content.model.dto.CourseTeacherDto;
import com.tg.content.model.po.CourseTeacher;
import com.tg.content.service.CourseTeacherService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.apache.ibatis.annotations.Delete;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name="课程授课教师接口")
public class CourseTeacherController {

    @Autowired
    CourseTeacherService courseTeacherService;



    @GetMapping("/courseTeacher/list/{courseId}")
    @Operation(summary = "请求教师信息")
    public List<CourseTeacherDto> getCourseTeacher(@PathVariable Long courseId){
        return courseTeacherService.getCourseTeacher(courseId);
    }

    @PostMapping("/courseTeacher")
    @Operation(summary = "添加教师")
    public CourseTeacherDto addCourseTeacher(@RequestBody CourseTeacher courseTeacher){
           return courseTeacherService.addCourseTeacher(courseTeacher);
    }

    @PutMapping("/courseTeacher")
    @Operation(summary = "修改教师")
    public CourseTeacherDto updateCourseTeacher(@RequestBody CourseTeacher courseTeacher){
        return courseTeacherService.updateCourseTeacher(courseTeacher);
    }


    @DeleteMapping("/courseTeacher/course/{courseId}/{id}")
    @Operation(summary = "删除教师")
    public void deleteCourseTeacher(@PathVariable Long courseId ,@PathVariable Long id){
        courseTeacherService.deleteCourseTeacher(courseId,id);
    }
}
