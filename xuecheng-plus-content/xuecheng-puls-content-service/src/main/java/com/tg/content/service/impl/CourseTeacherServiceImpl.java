package com.tg.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tg.base.exception.XueChengPlusException;
import com.tg.content.mapper.CourseTeacherMapper;
import com.tg.content.model.dto.CourseTeacherDto;
import com.tg.content.model.po.CourseTeacher;
import com.tg.content.service.CourseTeacherService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Service
public class CourseTeacherServiceImpl implements CourseTeacherService {

    @Autowired
    CourseTeacherMapper courseTeacherMapper;

    @Override
    public List<CourseTeacherDto> getCourseTeacher(Long courseId) {
        LambdaQueryWrapper<CourseTeacher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CourseTeacher::getCourseId,courseId);
        List<CourseTeacher> courseTeachers = courseTeacherMapper.selectList(queryWrapper);
        if (courseTeachers.size()==0) {
            XueChengPlusException.cast("该课程没有添加老师");
        }
        List<CourseTeacherDto> courseTeacherDtos = new ArrayList<>();
        courseTeachers.stream().forEach(courseTeacher -> {
            CourseTeacherDto courseTeacherDto = new CourseTeacherDto();
            BeanUtils.copyProperties(courseTeacher,courseTeacherDto);
            courseTeacherDtos.add(courseTeacherDto);
        });
        return courseTeacherDtos;
    }

    @Override
    public CourseTeacherDto addCourseTeacher(CourseTeacher courseTeacher) {
        if(courseTeacher.getId() == null){
            courseTeacherMapper.insert(courseTeacher);
        }else{
            courseTeacherMapper.updateById(courseTeacher);
        }
        CourseTeacher courseTeacherNew = courseTeacherMapper.selectById(courseTeacher.getId());
        CourseTeacherDto courseTeacherDto = new CourseTeacherDto();
        BeanUtils.copyProperties(courseTeacherNew,courseTeacherDto);
        return courseTeacherDto;
    }

    @Override
    public CourseTeacherDto updateCourseTeacher(CourseTeacher courseTeacher) {
        courseTeacherMapper.updateById(courseTeacher);
        CourseTeacher courseTeacherNew = courseTeacherMapper.selectById(courseTeacher.getId());
        CourseTeacherDto courseTeacherDto = new CourseTeacherDto();
        BeanUtils.copyProperties(courseTeacherNew,courseTeacherDto);
        return courseTeacherDto;
    }

    @Override
    public void deleteCourseTeacher( Long courseId ,Long id) {
        courseTeacherMapper.deleteById(id);
    }
}
