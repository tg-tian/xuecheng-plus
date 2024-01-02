package com.tg.content.model.dto;

import com.tg.content.model.po.Teachplan;
import com.tg.content.model.po.TeachplanMedia;
import lombok.Data;

import java.util.List;

@Data
public class TeachplanDto extends Teachplan {

    private TeachplanMedia teachplanMedia;

    private List<TeachplanDto> teachPlanTreeNodes;

}
