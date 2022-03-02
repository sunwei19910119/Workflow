package com.ruoyi.bpm.controller.definition;

import com.ruoyi.bpm.controller.definition.vo.form.*;
import com.ruoyi.bpm.convert.definition.BpmFormConvert;
import com.ruoyi.bpm.domain.definition.BpmFormDO;
import com.ruoyi.bpm.service.definition.BpmFormService;
import com.ruoyi.common.pojo.CommonResult;
import com.ruoyi.common.pojo.PageResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.List;

import static com.ruoyi.common.pojo.CommonResult.success;

@Api(tags = "管理后台 - 动态表单")
@RestController
@RequestMapping("/bpm/form")
@Validated
public class BpmFormController {

    @Resource
    private BpmFormService formService;

    @PostMapping("/create")
    @ApiOperation("创建动态表单")
    @PreAuthorize("@ss.hasPermi('bpm:form:create')")
    public CommonResult<Long> createForm(@Valid @RequestBody BpmFormCreateReqVO createReqVO) {
        return success(formService.createForm(createReqVO));
    }

    @PutMapping("/update")
    @ApiOperation("更新动态表单")
    @PreAuthorize("@ss.hasPermi('bpm:form:update')")
    public CommonResult<Boolean> updateForm(@Valid @RequestBody BpmFormUpdateReqVO updateReqVO) {
        formService.updateForm(updateReqVO);
        return success(true);
    }

    @DeleteMapping("/delete")
    @ApiOperation("删除动态表单")
    @ApiImplicitParam(name = "id", value = "编号", required = true, dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('bpm:form:delete')")
    public CommonResult<Boolean> deleteForm(@RequestParam("id") Long id) {
        formService.deleteForm(id);
        return success(true);
    }

    @GetMapping("/get")
    @ApiOperation("获得动态表单")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = Long.class)
    @PreAuthorize("@ss.hasPermi('bpm:form:query')")
    public CommonResult<BpmFormRespVO> getForm(@RequestParam("id") Long id) {
        BpmFormDO form = formService.getForm(id);
        return success(BpmFormConvert.INSTANCE.convert(form));
    }

    @GetMapping("/list-all-simple")
    @ApiOperation(value = "获得动态表单的精简列表", notes = "用于表单下拉框")
    public CommonResult<List<BpmFormSimpleRespVO>> getSimpleForms() {
        List<BpmFormDO> list = formService.getFormList();
        return success(BpmFormConvert.INSTANCE.convertList2(list));
    }

    @GetMapping("/page")
    @ApiOperation("获得动态表单分页")
    @PreAuthorize("@ss.hasPermi('bpm:form:query')")
    public CommonResult<PageResult<BpmFormRespVO>> getFormPage(@Valid BpmFormPageReqVO pageVO) {
        PageResult<BpmFormDO> pageResult = formService.getFormPage(pageVO);
        return success(BpmFormConvert.INSTANCE.convertPage(pageResult));
    }

}
