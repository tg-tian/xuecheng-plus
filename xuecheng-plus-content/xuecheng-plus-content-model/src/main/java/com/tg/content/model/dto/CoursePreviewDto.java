package com.tg.content.model.dto;

import lombok.Data;

import java.util.List;

@Data
public class CoursePreviewDto {
    CourseBaseInfoDto courseBase;


    //课程计划信息
    List<TeachplanDto> teachplans;

    //师资信息暂时不加...
}
