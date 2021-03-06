package com.ruoyi.bpm.controller.definition.vo.group;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.utils.DateUtils;
import lombok.*;
import java.util.*;
import io.swagger.annotations.*;

@ApiModel("管理后台 - 用户组 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BpmUserGroupRespVO extends BpmUserGroupBaseVO {

    @ApiModelProperty(value = "编号", required = true, example = "1024")
    private Long id;

    @ApiModelProperty(value = "创建时间", required = true)
    private Date createTime;

}
