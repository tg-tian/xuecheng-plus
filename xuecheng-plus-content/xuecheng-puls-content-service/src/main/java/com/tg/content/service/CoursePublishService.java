package com.tg.content.service;

import com.tg.content.model.dto.CoursePreviewDto;
import org.springframework.transaction.annotation.Transactional;

public interface CoursePublishService {

    CoursePreviewDto getCoursePreviewInfo(Long courseId);

    @Transactional
    void commitAudit(Long companyId,Long courseId);

    @Transactional
    public void publish(Long companyId,Long courseId);
}
