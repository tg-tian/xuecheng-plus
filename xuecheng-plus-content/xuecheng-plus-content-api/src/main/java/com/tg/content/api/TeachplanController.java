package com.tg.content.api;

import com.tg.content.model.dto.SaveTeachplanDto;
import com.tg.content.model.dto.TeachplanDto;
import com.tg.content.service.TeachplanService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name ="课程计划编辑接口")
@RestController
public class TeachplanController {
    @Autowired
    TeachplanService teachplanService;

    @Operation(summary = "查询课程计划树形结构")
    @GetMapping("/teachplan/{courseId}/tree-nodes")
    public List<TeachplanDto> getTreeNodes(@PathVariable Long courseId){

        return teachplanService.findTeachplanTree(courseId);

    }

    @Operation(summary = "课程计划创建或修改")
    @PostMapping("/teachplan")
    public void saveTeachplan( @RequestBody SaveTeachplanDto teachplan){

        teachplanService.saveTeachplan(teachplan);

    }

}
