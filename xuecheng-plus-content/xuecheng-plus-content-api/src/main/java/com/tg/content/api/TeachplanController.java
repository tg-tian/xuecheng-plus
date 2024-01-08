package com.tg.content.api;

import com.tg.content.model.dto.BindTeachplanMediaDto;
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

    @Operation(summary = "删除课程计划")
    @DeleteMapping("/teachplan/{id}")
    public void deleteTeachplan( @PathVariable Long id){

        teachplanService.deleteTeachplan(id);

    }

    @Operation(summary = "向上移动课程计划")
    @PostMapping("/teachplan/moveup/{id}")
    public void moveupTeachplan( @PathVariable Long id){

        teachplanService.moveup(id);
    }

    @Operation(summary = "向下移动课程计划")
    @PostMapping("/teachplan/movedown/{id}")
    public void movedownTeachplan( @PathVariable Long id){
        teachplanService.movedown(id);
    }

    @Operation(summary = "课程计划和媒资信息绑定")
    @PostMapping("/teachplan/association/media")
    public void associationMedia(@RequestBody BindTeachplanMediaDto bindTeachplanMediaDto){
        teachplanService.associationMedia(bindTeachplanMediaDto);
    }

    @Operation(summary = "课程计划和媒资信息删除")
    @DeleteMapping("/teachplan/association/media/{teachPlanId}/{mediaId}")
    public void deleteAssociationMedia(@PathVariable("teachPlanId") Long teachPlanId , @PathVariable("mediaId") String mediaId){
        teachplanService.deleteTeachplanMedia(teachPlanId,mediaId);
    }

}
