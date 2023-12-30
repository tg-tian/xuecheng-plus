package com.tg.content.api;

import com.tg.base.model.PageParams;
import com.tg.base.model.PageResult;
import com.tg.content.model.dto.QueryCourseParamsDto;
import com.tg.content.model.po.CourseBase;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name ="课程信息编辑接口")
@RestController
public class CourseBaseInfoController {

    @Operation(summary = "课程查询接口")
   @PostMapping("/course/list")
    public PageResult<CourseBase> list(PageParams pageParams , @RequestBody(required = false) QueryCourseParamsDto queryCourseParamsDto){
       System.out.println(1);
        return null;
    }

}
