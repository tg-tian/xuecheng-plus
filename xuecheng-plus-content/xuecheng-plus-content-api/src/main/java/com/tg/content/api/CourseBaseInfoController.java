package com.tg.content.api;

import com.tg.base.exception.ValidationGroups;
import com.tg.base.model.PageParams;
import com.tg.base.model.PageResult;
import com.tg.content.model.dto.AddCourseDto;
import com.tg.content.model.dto.CourseBaseInfoDto;
import com.tg.content.model.dto.EditCourseDto;
import com.tg.content.model.dto.QueryCourseParamsDto;
import com.tg.content.model.po.CourseBase;


import com.tg.content.service.CourseBaseInfoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Tag(name ="课程信息编辑接口")
@RestController
public class CourseBaseInfoController {

    @Autowired
    CourseBaseInfoService courseBaseInfoService;
    @Operation(summary = "课程分页查询")
   @PostMapping("/course/list")
    public PageResult<CourseBase> list(PageParams pageParams , @RequestBody(required = false) QueryCourseParamsDto queryCourseParamsDto){

        return courseBaseInfoService.queryCourseBaseList(pageParams,queryCourseParamsDto);

    }

    @Operation(summary = "新增课程")
    @PostMapping("/course")
    public CourseBaseInfoDto createCourseBase(@RequestBody @Validated(ValidationGroups.Insert.class) AddCourseDto addCourseDto){

        Long companyId = 1232141425L;

        CourseBaseInfoDto courseBase = courseBaseInfoService.createCourseBase(companyId, addCourseDto);

        return courseBase;

    }

    @Operation(summary = "课程查询")
    @GetMapping("/course/{id}")
    public CourseBaseInfoDto getCourseBaseById(@PathVariable Long id){
        System.out.println(id);

        return courseBaseInfoService.getCourseBaseInfo(id);

    }

    @Operation(summary = "修改课程")
    @PutMapping("/course")
    public CourseBaseInfoDto modifyCourseBase(@RequestBody @Validated(ValidationGroups.Update.class) EditCourseDto editCourseDto){

        Long companyId = 1232141425L;
        return courseBaseInfoService.updateCourseBase(companyId, editCourseDto);


    }

}
