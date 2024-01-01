package com.tg.content.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.tg.content.model.dto.CourseCategoryTreeDto;
import com.tg.content.model.po.CourseCategory;

import java.util.List;

/**
 * <p>
 * 课程分类 Mapper 接口
 * </p>
 *
 * @author itcast
 */
public interface CourseCategoryMapper extends BaseMapper<CourseCategory> {

    public List<CourseCategoryTreeDto> selectTreeNodes(String id);
}
