package com.ruoyi.bpm.controller.definition;

import com.ruoyi.common.pojo.CommonResult;
import com.ruoyi.common.pojo.PageResult;
import com.ruoyi.bpm.controller.definition.vo.process.BpmProcessDefinitionListReqVO;
import com.ruoyi.bpm.controller.definition.vo.process.BpmProcessDefinitionPageItemRespVO;
import com.ruoyi.bpm.controller.definition.vo.process.BpmProcessDefinitionPageReqVO;
import com.ruoyi.bpm.controller.definition.vo.process.BpmProcessDefinitionRespVO;
import com.ruoyi.bpm.service.definition.BpmProcessDefinitionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

import static com.ruoyi.common.pojo.CommonResult.success;

@Api(tags = "管理后台 - 流程定义")
@RestController
@RequestMapping("/bpm/process-definition")
@Validated
public class BpmProcessDefinitionController {

    @Resource
    private BpmProcessDefinitionService bpmDefinitionService;

    @GetMapping ("/page")
    @ApiOperation(value = "获得流程定义分页")
    @PreAuthorize("@ss.hasPermi('bpm:process-definition:query')")
    public CommonResult<PageResult<BpmProcessDefinitionPageItemRespVO>> getProcessDefinitionPage(
            BpmProcessDefinitionPageReqVO pageReqVO) {
        return success(bpmDefinitionService.getProcessDefinitionPage(pageReqVO));
    }

    @GetMapping ("/list")
    @ApiOperation(value = "获得流程定义列表")
    @PreAuthorize("@ss.hasPermi('bpm:process-definition:query')")
    public CommonResult<List<BpmProcessDefinitionRespVO>> getProcessDefinitionList(
            BpmProcessDefinitionListReqVO listReqVO) {
        return success(bpmDefinitionService.getProcessDefinitionList(listReqVO));
    }

    @GetMapping ("/get-bpmn-xml")
    @ApiOperation(value = "获得流程定义的 BPMN XML")
    @ApiImplicitParam(name = "id", value = "编号", required = true, example = "1024", dataTypeClass = String.class)
    @PreAuthorize("@ss.hasPermi('bpm:process-definition:query')")
    public CommonResult<String> getProcessDefinitionBpmnXML(@RequestParam("id") String id) {
        String bpmnXML = bpmDefinitionService.getProcessDefinitionBpmnXML(id);
        return success("操作成功",bpmnXML);
    }

}
