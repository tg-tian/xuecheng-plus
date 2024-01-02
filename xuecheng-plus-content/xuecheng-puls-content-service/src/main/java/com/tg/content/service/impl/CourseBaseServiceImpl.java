package com.tg.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.tg.base.exception.XueChengPlusException;
import com.tg.base.model.PageParams;
import com.tg.base.model.PageResult;
import com.tg.content.mapper.*;
import com.tg.content.model.dto.AddCourseDto;
import com.tg.content.model.dto.CourseBaseInfoDto;
import com.tg.content.model.dto.EditCourseDto;
import com.tg.content.model.dto.QueryCourseParamsDto;
import com.tg.content.model.po.*;
import com.tg.content.service.CourseBaseInfoService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class CourseBaseServiceImpl implements CourseBaseInfoService {

    @Autowired
    CourseBaseMapper courseBaseMapper;

    @Autowired
    CourseMarketMapper courseMarketMapper;

    @Autowired
    CourseCategoryMapper courseCategoryMapper;

    @Autowired
    CourseTeacherMapper courseTeacherMapper;

    @Autowired
    TeachplanMapper teachplanMapper;

    @Autowired
    TeachplanMediaMapper teachplanMediaMapper;

    @Override
    public PageResult<CourseBase> queryCourseBaseList(PageParams pageParams, QueryCourseParamsDto queryCourseParamsDto) {

        LambdaQueryWrapper<CourseBase> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(queryCourseParamsDto.getCourseName()),CourseBase::getName,queryCourseParamsDto.getCourseName());
        queryWrapper.eq(StringUtils.isNotEmpty(queryCourseParamsDto.getAuditStatus()),CourseBase::getAuditStatus,queryCourseParamsDto.getAuditStatus());
        Page<CourseBase> page = new Page<>(pageParams.getPageNo(), pageParams.getPageSize());

        Page<CourseBase> pageResult = courseBaseMapper.selectPage(page, queryWrapper);

        PageResult<CourseBase> courseBasePageResult = new PageResult<>();
        courseBasePageResult.setItems(pageResult.getRecords());
        courseBasePageResult.setCounts(pageResult.getTotal());
        courseBasePageResult.setPage(pageParams.getPageNo());
        courseBasePageResult.setPageSize(pageParams.getPageSize());

        return courseBasePageResult;
    }

    @Override
    public CourseBaseInfoDto createCourseBase(Long companyId,AddCourseDto dto) {

        CourseBase courseBase = new CourseBase();
        BeanUtils.copyProperties(dto,courseBase);
        courseBase.setCompanyId(companyId);
        courseBase.setCreateDate(LocalDateTime.now());
        courseBase.setAuditStatus("202002");
        courseBase.setStatus("203001");
        int insert = courseBaseMapper.insert(courseBase);
        if(insert<=0){
            throw new RuntimeException("添加课程失败");
        }

        CourseMarket courseMarket = new CourseMarket();
        BeanUtils.copyProperties(dto,courseMarket);
        courseMarket.setId(courseBase.getId());
        int i = saveCourseMarket(courseMarket);
        if (i<=0) {
            throw new RuntimeException("保存课程营销信息失败");
        }
        return getCourseBaseInfo(courseBase.getId());

    }

    @Override
    public CourseBaseInfoDto getCourseBaseInfo(Long courseId){
        CourseBase courseBase = courseBaseMapper.selectById(courseId);
        if (courseBase == null) {
            return null;
        }
        CourseMarket courseMarket = courseMarketMapper.selectById(courseId);
        CourseBaseInfoDto courseBaseInfoDto = new CourseBaseInfoDto();
        BeanUtils.copyProperties(courseBase,courseBaseInfoDto);
        if (courseMarket!=null) {
            BeanUtils.copyProperties(courseMarket,courseBaseInfoDto);
        }


        CourseCategory courseCategoryBySt = courseCategoryMapper.selectById(courseBase.getSt());
        courseBaseInfoDto.setStName(courseCategoryBySt.getName());
        CourseCategory courseCategoryByMt = courseCategoryMapper.selectById(courseBase.getMt());
        courseBaseInfoDto.setMtName(courseCategoryByMt.getName());


        return courseBaseInfoDto;
    }

    @Override
    public CourseBaseInfoDto updateCourseBase(Long companyId, EditCourseDto dto) {
        Long id = dto.getId();
        CourseBase courseBase = courseBaseMapper.selectById(id);
        CourseMarket courseMarket = courseMarketMapper.selectById(id);
        if (courseBase == null) {
            XueChengPlusException.cast("课程不存在");
        }
        if (!companyId.equals(courseBase.getCompanyId())) {
            XueChengPlusException.cast("不能修改不是所属公司的课程信息");
        }
        BeanUtils.copyProperties(dto,courseBase);
        courseBase.setChangeDate(LocalDateTime.now());
        BeanUtils.copyProperties(dto,courseMarket);
        courseBaseMapper.updateById(courseBase);
        saveCourseMarket(courseMarket);

        return getCourseBaseInfo(id);

    }

    @Override
    public void deleteCourseBase(Long id) {
        courseBaseMapper.deleteById(id);
        courseMarketMapper.deleteById(id);
        LambdaQueryWrapper<CourseTeacher> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CourseTeacher::getCourseId,id);
        courseTeacherMapper.delete(queryWrapper);
        LambdaQueryWrapper<Teachplan> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1.eq(Teachplan::getCourseId,id);
        teachplanMapper.delete(queryWrapper1);
        LambdaQueryWrapper<TeachplanMedia> queryWrapper2 = new LambdaQueryWrapper<>();
        queryWrapper2.eq(TeachplanMedia::getCourseId,id);
        teachplanMediaMapper.delete(queryWrapper2);
    }

    private int saveCourseMarket(CourseMarket courseMarket){
        String charge = courseMarket.getCharge();
        if(StringUtils.isEmpty(charge)){
            throw new RuntimeException("收费规则为空");
        }
        if(charge.equals("201001")){
            if(courseMarket.getPrice() == null || courseMarket.getPrice().floatValue()<=0){
                throw new RuntimeException("课程为收费价格不能为空且必须大于0");
            }
        }
        Long id = courseMarket.getId();
        CourseMarket oldCourseMarket = courseMarketMapper.selectById(id);
        if(oldCourseMarket == null){
            int insert = courseMarketMapper.insert(courseMarket);
            return insert;
        }else {

            BeanUtils.copyProperties(courseMarket,oldCourseMarket);
            oldCourseMarket.setId(courseMarket.getId());
            int insert = courseMarketMapper.updateById(oldCourseMarket);
            return insert;
        }
    }
}
