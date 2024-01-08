package com.tg.content.service;

import com.tg.content.model.dto.BindTeachplanMediaDto;
import com.tg.content.model.dto.SaveTeachplanDto;
import com.tg.content.model.dto.TeachplanDto;
import com.tg.content.model.po.TeachplanMedia;
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

    @Transactional
    TeachplanMedia associationMedia(BindTeachplanMediaDto bindTeachplanMediaDto);

    void deleteTeachplanMedia(Long teachplanId, String mediaId);
}
