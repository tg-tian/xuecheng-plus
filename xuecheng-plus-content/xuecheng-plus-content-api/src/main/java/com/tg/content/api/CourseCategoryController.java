package com.tg.content.api;

import com.tg.base.model.PageParams;
import com.tg.base.model.PageResult;
import com.tg.content.model.dto.CourseCategoryTreeDto;
import com.tg.content.model.dto.QueryCourseParamsDto;
import com.tg.content.model.po.CourseBase;
import com.tg.content.service.CourseBaseInfoService;
import com.tg.content.service.CourseCategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
public class CourseCategoryController {

    @Autowired
    CourseCategoryService courseCategoryService;

   @GetMapping("/course-category/tree-nodes")
    public List<CourseCategoryTreeDto> queryTreeNodes(){

        return courseCategoryService.queryTreeNodes("1");

    }

}
