package com.ruoyi.workflow.engine.controller;

import com.ruoyi.base.ActionResult;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.JsonUtil;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.workflow.engine.designer.DesignerAdapterUtil;
import com.ruoyi.workflow.engine.designer.entity.ChildNode;
import com.ruoyi.workflow.engine.designer.entity.FlowDesignerModel;
import com.ruoyi.workflow.engine.designer.entity.FormOperate;
import com.ruoyi.workflow.engine.entity.FlowDesignerEntity;
import com.ruoyi.workflow.engine.entity.HistoricActivity;
import com.ruoyi.workflow.engine.entity.MyApplyVo;
import com.ruoyi.workflow.engine.entity.TaskVo;
import com.ruoyi.workflow.engine.event.WorkflowEvent;
import com.ruoyi.workflow.engine.service.FlowDesignerService;
import com.ruoyi.workflow.engine.service.IProcessService;
import lombok.AllArgsConstructor;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/activiti/process")
@AllArgsConstructor
public class ActivitiProcessController extends BaseController {

    private RepositoryService repositoryService;
    private HistoryService historyService;
    private ProcessEngine processEngine;
    private IProcessService processService;
    private RuntimeService runtimeService;
    private TaskService taskService;
    private ApplicationEventPublisher applicationEventPublisher;
    private FlowDesignerService flowDesignerService;

    /**
     * 审批历史列表
     */
    @PostMapping("/listHistory")
    @ResponseBody
    public TableDataInfo listHistory(@RequestBody HistoricActivity historicActivity) {
        startPage();
        List<HistoricActivity> list = processService.selectHistoryList(historicActivity);
        return getDataTable(list);
    }

    /**
     * 获取流程发起表单的查看和修改的权限
     */
    @GetMapping("/deisnger/startFormOperates/{processDefKey}")
    public ActionResult getStartFlowFormOperate(@PathVariable(name = "processDefKey") String processDefKey) {
        FlowDesignerEntity designer = flowDesignerService.getInfoByEnCode(processDefKey);
        FlowDesignerModel model = JsonUtil.getJsonToBean(designer.getJson(), FlowDesignerModel.class);
        List<FormOperate> formOperates = model.getProcessData().getProperties().getFormOperates();

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("formOperates", formOperates);
        return ActionResult.success(result);
    }

    /**
     * 获取表单的字段查看和修改的权限
     */
    @GetMapping("/designer/formOperates/{processInstanceId}/{taskId}")
    public ActionResult getFormOperate(@PathVariable(name = "processInstanceId") String processInstanceId, @PathVariable(name = "taskId") String taskId) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
        String processDefinitionId = "";
        if (processInstance == null) {
            HistoricProcessInstance historicProcessInstance =
                historyService.createHistoricProcessInstanceQuery().processInstanceId(processInstanceId).singleResult();
            processDefinitionId = historicProcessInstance.getProcessDefinitionId();
        } else {
            processDefinitionId = processInstance.getProcessDefinitionId();
        }
        BpmnModel bpmn = repositoryService.getBpmnModel(processDefinitionId);
        String json = bpmn.getMainProcess().getAttributeValue("http://activiti.org/bpmn", "designerJSON");
        FlowDesignerModel model = JsonUtil.getJsonToBean(json, FlowDesignerModel.class);

        String taskDefKey = "";
        // 查询taskDefKey
        org.activiti.engine.task.Task task = taskService.createTaskQuery().taskId(taskId).singleResult();

        if (task == null) {
            HistoricTaskInstance oldTask = historyService.createHistoricTaskInstanceQuery().taskId(taskId).singleResult();
            taskDefKey = oldTask.getTaskDefinitionKey();
        } else {
            taskDefKey = task.getTaskDefinitionKey();
        }

        // 查询当前流程的字段权限
        ChildNode node = DesignerAdapterUtil.getChildNode(json, taskDefKey);
        List<FormOperate> formOperates = node.getProperties().getFormOperates();
        Map<String, Object> result = new HashMap<String, Object>();
        // result.put("task", task);
        result.put("formOperates", formOperates);
        return ActionResult.success(result);
    }

    /**
     * 获取流程图（自定义流程图）
     */
    @GetMapping("/designer/{instanceId}")
    public ActionResult getDesignerImg(@PathVariable(name = "instanceId") String pProcessInstanceId, HttpServletResponse response) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(pProcessInstanceId).singleResult();
        String processDefinitionId = "";
        if (processInstance == null) {
            HistoricProcessInstance historicProcessInstance =
                historyService.createHistoricProcessInstanceQuery().processInstanceId(pProcessInstanceId).singleResult();
            processDefinitionId = historicProcessInstance.getProcessDefinitionId();
        } else {
            processDefinitionId = processInstance.getProcessDefinitionId();
        }
        BpmnModel bpmn = repositoryService.getBpmnModel(processDefinitionId);
        String json = bpmn.getMainProcess().getAttributeValue("http://activiti.org/bpmn", "designerJSON");
        FlowDesignerModel model = JsonUtil.getJsonToBean(json, FlowDesignerModel.class);

        // 查询已办理的流程
        List<HistoricTaskInstance> historicTasks = historyService.createHistoricTaskInstanceQuery().processInstanceId(pProcessInstanceId).list();
        List<String> completeTasks = historicTasks.stream().map(HistoricTaskInstance::getTaskDefinitionKey).collect(Collectors.toList());

        // 查询待办流程
        List<org.activiti.engine.task.Task> tasks = taskService.createTaskQuery().processInstanceId(pProcessInstanceId).list();
        List<String> currentTasks = tasks.stream().map(Task::getTaskDefinitionKey).collect(Collectors.toList());

        Map<String, Object> result = new HashMap<String, Object>();
        result.put("model", model);
        result.put("completeTasks", completeTasks);
        result.put("tasks", currentTasks);// 未来可能扩展并行流程，所以这里是多个task
        return ActionResult.success(result);
    }

    /**
     * 转办
     */
    @PostMapping("/delegate")
    @ResponseBody
    public AjaxResult delegate(String taskId, String delegateToUser) {
        processService.delegate(taskId, SecurityUtils.getUsername(), delegateToUser);
        return success();
    }

    /**
     * 撤销流程实例
     */
    @PostMapping("/cancelApply")
    @ResponseBody
    @Transactional
    public AjaxResult cancelApply(String instanceId) {
        // 通过instanceId查询
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(instanceId).singleResult();

        // 广播撤销事件
        applicationEventPublisher.publishEvent(new WorkflowEvent(this, processInstance, WorkflowEvent.EVENT_CANCEL_APPLY));
        processService.cancelApply(instanceId, "用户撤销");
        return success("撤销成功");
    }

    /**
     * 激活/挂起流程实例
     */
    @PostMapping("/suspendOrActiveApply")
    @ResponseBody
    public AjaxResult suspendOrActiveApply(String instanceId, String suspendState) {
        processService.suspendOrActiveApply(instanceId, suspendState);
        return success();
    }

    /**
     * 我的待办列表
     */
    @GetMapping("/taskList")
    @ResponseBody
    public TableDataInfo taskList(TaskVo taskVo) {
        return processService.findTodoTasks(taskVo);
    }

    /**
     * 办理任务
     */
    @PostMapping("/complete")
    @ResponseBody
    public AjaxResult complete(String taskId, String instanceId, String variables) {
        processService.complete(taskId, instanceId, variables);
        return success();
    }

    /**
     * 我的已办列表
     */
    @GetMapping("/taskDoneList")
    @ResponseBody
    public TableDataInfo taskDoneList(TaskVo taskVo) {
        return processService.findDoneTasks(taskVo);
    }

    /**
     * 我发起的流程
     */
    @GetMapping("/taskApplyList")
    @ResponseBody
    public TableDataInfo taskApplyList(MyApplyVo myApplyVo) {
        return processService.findTaskApplyedByMe(myApplyVo);
    }

}
