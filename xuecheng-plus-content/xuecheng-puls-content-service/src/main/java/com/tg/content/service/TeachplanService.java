package com.tg.content.service;

import com.tg.content.model.dto.SaveTeachplanDto;
import com.tg.content.model.dto.TeachplanDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TeachplanService {
    List<TeachplanDto> findTeachplanTree(long courseId);

    @Transactional
    public void saveTeachplan(SaveTeachplanDto teachplanDto);
}
