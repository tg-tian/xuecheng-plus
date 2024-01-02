package com.tg.content.service;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tg.base.model.PageParams;
import com.tg.base.model.PageResult;
import com.tg.content.model.dto.AddCourseDto;
import com.tg.content.model.dto.CourseBaseInfoDto;
import com.tg.content.model.dto.EditCourseDto;
import com.tg.content.model.dto.QueryCourseParamsDto;
import com.tg.content.model.po.CourseBase;
import org.apache.commons.lang.StringUtils;
import org.springframework.transaction.annotation.Transactional;

public interface CourseBaseInfoService {

   PageResult<CourseBase> queryCourseBaseList(PageParams pageParams , QueryCourseParamsDto queryCourseParamsDto);

   @Transactional
   CourseBaseInfoDto createCourseBase(Long companyId , AddCourseDto addCourseDto);


   public CourseBaseInfoDto getCourseBaseInfo(Long courseId);

   @Transactional
   public CourseBaseInfoDto updateCourseBase(Long companyId, EditCourseDto dto);

   @Transactional
    void deleteCourseBase(Long id);
}
