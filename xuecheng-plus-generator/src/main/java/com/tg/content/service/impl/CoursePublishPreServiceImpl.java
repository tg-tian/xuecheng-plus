package com.tg.content.service.impl;

import com.tg.content.model.po.CoursePublishPre;
import com.tg.content.mapper.CoursePublishPreMapper;
import com.tg.content.service.CoursePublishPreService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 * 课程发布 服务实现类
 * </p>
 *
 * @author itcast
 */
@Slf4j
@Service
public class CoursePublishPreServiceImpl extends ServiceImpl<CoursePublishPreMapper, CoursePublishPre> implements CoursePublishPreService {

}
