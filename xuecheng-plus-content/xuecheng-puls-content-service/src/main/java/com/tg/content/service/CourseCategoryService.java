package com.tg.content.service;

import com.tg.content.model.dto.CourseCategoryTreeDto;

import java.util.List;

public interface CourseCategoryService {

    public List<CourseCategoryTreeDto> queryTreeNodes(String id);

}
