package com.tg.content.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tg.base.exception.XueChengPlusException;
import com.tg.content.mapper.TeachplanMapper;
import com.tg.content.mapper.TeachplanMediaMapper;
import com.tg.content.model.dto.SaveTeachplanDto;
import com.tg.content.model.dto.TeachplanDto;
import com.tg.content.model.po.Teachplan;
import com.tg.content.model.po.TeachplanMedia;
import com.tg.content.service.TeachplanService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Wrapper;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class TeachplanServiceImpl implements TeachplanService {

    @Autowired
    TeachplanMapper teachplanMapper;

    @Autowired
    TeachplanMediaMapper teachplanMediaMapper;

    @Override
    public List<TeachplanDto> findTeachplanTree(long courseId) {
        return teachplanMapper.selectTreeNodes(courseId);
    }

    @Override
    public void saveTeachplan(SaveTeachplanDto teachplanDto) {
        Long id = teachplanDto.getId();
        if (id != null) {

            Teachplan teachplan = teachplanMapper.selectById(id);
            BeanUtils.copyProperties(teachplanDto,teachplan);
            teachplanMapper.updateById(teachplan);
        }else {
            int count = getTeachplanCount(teachplanDto.getCourseId(), teachplanDto.getParentid());
            Teachplan teachplanNew = new Teachplan();
            //设置排序号
            teachplanNew.setOrderby(count+1);
            BeanUtils.copyProperties(teachplanDto,teachplanNew);

            teachplanMapper.insert(teachplanNew);
        }
    }

    @Override
    public void deleteTeachplan(Long id) {
        Teachplan teachplan = teachplanMapper.selectById(id);
        Long parentid = teachplan.getParentid();
        if (parentid != 0) {
            TeachplanMedia teachplanMedia = teachplanMediaMapper.selectById(id);
            if (teachplanMedia != null) {
                teachplanMediaMapper.deleteById(id);
            }
            teachplanMapper.deleteById(id);
        }else{
            LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(Teachplan::getParentid,id);
            List<Teachplan> teachplans = teachplanMapper.selectList(queryWrapper);
            if (teachplans.size()>0) {
                XueChengPlusException.cast("课程计划信息还有子级信息，无法操作");
            }else{
                teachplanMapper.deleteById(id);
            }
        }


    }

    @Override
    public void moveup(Long id) {
        Teachplan teachplan = teachplanMapper.selectById(id);
        Long parentid = teachplan.getParentid();
        Long courseId = teachplan.getCourseId();
        LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Teachplan::getParentid,parentid)
                .eq(Teachplan::getCourseId,courseId);
        List<Teachplan> teachplans = teachplanMapper.selectList(queryWrapper);
        if(teachplans.size()>1){
            List<Teachplan> sortedTeachplans = teachplans.stream().sorted(((o1, o2) ->  o2.getOrderby()-o1.getOrderby())).toList();
           Teachplan up = sortedTeachplans.stream().filter(item -> item.getOrderby() < teachplan.getOrderby()).findFirst().orElse(null);
           if(up != null){
               Integer orderby = teachplan.getOrderby();
               Integer orderby1 = up.getOrderby();
               teachplan.setOrderby(orderby1);
               up.setOrderby(orderby);
               teachplanMapper.updateById(teachplan);
               teachplanMapper.updateById(up);
           }
        }

    }

    @Override
    public void movedown(Long id) {
        Teachplan teachplan = teachplanMapper.selectById(id);
        Long parentid = teachplan.getParentid();
        Long courseId = teachplan.getCourseId();
        LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Teachplan::getParentid,parentid)
                .eq(Teachplan::getCourseId,courseId);
        List<Teachplan> teachplans = teachplanMapper.selectList(queryWrapper);
        if(teachplans.size()>1){
            List<Teachplan> sortedTeachplans = teachplans.stream().sorted((Comparator.comparingInt(Teachplan::getOrderby))).toList();
            Teachplan down = sortedTeachplans.stream().filter(item -> item.getOrderby() > teachplan.getOrderby()).findFirst().orElse(null);
            if(down != null){
                Integer orderby = teachplan.getOrderby();
                Integer orderby1 = down.getOrderby();
                teachplan.setOrderby(orderby1);
                down.setOrderby(orderby);
                teachplanMapper.updateById(teachplan);
                teachplanMapper.updateById(down);
            }
        }
    }

    private int getTeachplanCount(Long courseId, Long parentId) {
        LambdaQueryWrapper<Teachplan> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Teachplan::getCourseId,courseId)
                .eq(Teachplan::getParentid,parentId);
        Long count = teachplanMapper.selectCount(queryWrapper);
        return  count.intValue();

    }

}
