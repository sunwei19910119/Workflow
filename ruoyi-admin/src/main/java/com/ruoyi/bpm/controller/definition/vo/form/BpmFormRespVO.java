package com.ruoyi.bpm.controller.definition.vo.form;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.utils.DateUtils;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

@ApiModel("管理后台 - 动态表单 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BpmFormRespVO extends BpmFormBaseVO {

    @ApiModelProperty(value = "表单编号", required = true, example = "1024")
    private Long id;

    @ApiModelProperty(value = "表单的配置", required = true, notes = "JSON 字符串")
    @NotNull(message = "表单的配置不能为空")
    private String conf;

    @ApiModelProperty(value = "表单项的数组", required = true, notes = "JSON 字符串的数组")
    @NotNull(message = "表单项的数组不能为空")
    private List<String> fields;

    @ApiModelProperty(value = "创建时间", required = true)
    @JsonFormat(pattern = DateUtils.FORMAT_YEAR_MONTH_DAY_HOUR_MINUTE_SECOND, timezone = DateUtils.TIME_ZONE_DEFAULT)
    private Date createTime;

}
