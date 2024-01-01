package com.tg.content.service.impl;

import com.tg.content.mapper.CourseCategoryMapper;
import com.tg.content.model.dto.CourseCategoryTreeDto;
import com.tg.content.service.CourseCategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CourseCategoryServiceImpl implements CourseCategoryService {

    @Autowired
    CourseCategoryMapper courseCategoryMapper;
    @Override
    public List<CourseCategoryTreeDto> queryTreeNodes(String id) {
        List<CourseCategoryTreeDto> courseCategoryTreeDtos = courseCategoryMapper.selectTreeNodes(id);

        Map<String, CourseCategoryTreeDto> matTemp = courseCategoryTreeDtos.stream()
                .filter(course -> !id.equals(course.getId()))
                .collect(Collectors.toMap(key -> key.getId(), value -> value, (key1, key2) -> key2));

        courseCategoryTreeDtos.stream()
                .filter(course -> !id.equals(course.getId()))
                .forEach(item ->{
                    String parentid = item.getParentid();

                    if(!id.equals(parentid)){
                        CourseCategoryTreeDto parent = matTemp.get(parentid);
                        if(ObjectUtils.isEmpty(parent.getChildrenTreeNodes())){
                            parent.setChildrenTreeNodes(new ArrayList<CourseCategoryTreeDto>());
                        }
                        parent.getChildrenTreeNodes().add(item);
                    }
                });

       return courseCategoryTreeDtos.stream().filter(course -> id.equals(course.getParentid())).toList();


    }
}
