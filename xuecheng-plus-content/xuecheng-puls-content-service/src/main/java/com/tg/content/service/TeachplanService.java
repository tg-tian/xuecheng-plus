package com.tg.content.service;

import com.tg.content.model.dto.SaveTeachplanDto;
import com.tg.content.model.dto.TeachplanDto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface TeachplanService {
    List<TeachplanDto> findTeachplanTree(long courseId);

    @Transactional
    void saveTeachplan(SaveTeachplanDto teachplanDto);

    @Transactional
    void deleteTeachplan(Long id);

    void moveup(Long id);

    void movedown(Long id);
}
