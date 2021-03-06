package com.ruoyi.bpm.service.task.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.ruoyi.admin.api.domain.AdminUserRespDTO;
import com.ruoyi.admin.api.domain.DeptRespDTO;
import com.ruoyi.admin.api.service.AdminUserApi;
import com.ruoyi.admin.api.service.DeptApi;
import com.ruoyi.common.pojo.PageResult;
import com.ruoyi.common.utils.number.NumberUtils;
import com.ruoyi.common.utils.object.PageUtils;
import com.ruoyi.bpm.controller.task.vo.task.*;
import com.ruoyi.bpm.convert.task.BpmTaskConvert;
import com.ruoyi.bpm.core.util.ActivitiUtils;
import com.ruoyi.bpm.domain.task.BpmTaskExtDO;
import com.ruoyi.bpm.enums.task.BpmProcessInstanceResultEnum;
import com.ruoyi.bpm.mapper.task.BpmTaskExtMapper;
import com.ruoyi.bpm.service.message.BpmMessageService;
import com.ruoyi.bpm.service.task.BpmProcessInstanceService;
import com.ruoyi.bpm.service.task.BpmTaskService;
import lombok.extern.slf4j.Slf4j;
import org.activiti.engine.HistoryService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.history.HistoricTaskInstanceQuery;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Resource;
import javax.validation.Valid;
import java.util.*;

import static com.ruoyi.common.exception.util.ServiceExceptionUtil.exception;
import static com.ruoyi.common.utils.collection.CollectionUtils.convertMap;
import static com.ruoyi.common.utils.collection.CollectionUtils.convertSet;
import static com.ruoyi.bpm.enums.ErrorCodeConstants.*;

/**
 * ?????????????????? Service ?????????
 *
 * @author jason
 * @author ????????????
 */
@Slf4j
@Service
public class BpmTaskServiceImpl implements BpmTaskService {

    @Resource
    private TaskService taskService;
    @Resource
    private HistoryService  historyService;

    @Resource
    private AdminUserApi adminUserApi;
    @Resource
    private DeptApi deptApi;
    @Resource
    @Lazy // ??????????????????
    private BpmProcessInstanceService processInstanceService;
    @Resource
    private BpmMessageService messageService;

    @Resource
    private BpmTaskExtMapper taskExtMapper;

    @Override
    public List<Task> getRunningTaskListByProcessInstanceId(String processInstanceId) {
        return taskService.createTaskQuery().processInstanceId(processInstanceId).list();
    }

    @Override
    public List<BpmTaskRespVO> getTaskListByProcessInstanceId(String processInstanceId) {
        // ??????????????????
        List<HistoricTaskInstance> tasks = historyService.createHistoricTaskInstanceQuery()
                .processInstanceId(processInstanceId)
                .orderByHistoricTaskInstanceStartTime().desc() // ??????????????????
                .list();
        if (CollUtil.isEmpty(tasks)) {
            return Collections.emptyList();
        }

        // ?????? TaskExtDO Map
        List<BpmTaskExtDO> bpmTaskExtDOs = taskExtMapper.selectListByTaskIds(convertSet(tasks, HistoricTaskInstance::getId));
        Map<String, BpmTaskExtDO> bpmTaskExtDOMap = convertMap(bpmTaskExtDOs, BpmTaskExtDO::getTaskId);
        // ?????? ProcessInstance Map
        HistoricProcessInstance processInstance = processInstanceService.getHistoricProcessInstance(processInstanceId);
        // ?????? User Map
        Set<Long> userIds = convertSet(tasks, task -> NumberUtils.parseLong(task.getAssignee()));
        userIds.add(NumberUtils.parseLong(processInstance.getStartUserId()));
        Map<Long, AdminUserRespDTO> userMap = adminUserApi.getUserMap(userIds);
        // ?????? Dept Map
        Map<Long, DeptRespDTO> deptMap = deptApi.getDeptMap(convertSet(userMap.values(), AdminUserRespDTO::getDeptId));

        // ????????????
        return BpmTaskConvert.INSTANCE.convertList3(tasks, bpmTaskExtDOMap, processInstance, userMap, deptMap);
    }

    @Override
    public List<Task> getTasksByProcessInstanceId(String processInstanceId) {
        if (StrUtil.isEmpty(processInstanceId)) {
            return Collections.emptyList();
        }
        return taskService.createTaskQuery().processInstanceId(processInstanceId).list();
    }

    @Override
    public List<Task> getTasksByProcessInstanceIds(List<String> processInstanceIds) {
        if (CollUtil.isEmpty(processInstanceIds)) {
            return Collections.emptyList();
        }
        return taskService.createTaskQuery().processInstanceIdIn(processInstanceIds).list();
    }

    @Override
    public PageResult<BpmTaskTodoPageItemRespVO> getTodoTaskPage(Long userId, BpmTaskTodoPageReqVO pageVO) {
        // ??????????????????
        TaskQuery taskQuery = taskService.createTaskQuery()
                .taskAssignee(String.valueOf(userId)) // ???????????????
                .orderByTaskCreateTime().desc(); // ??????????????????
        if (StrUtil.isNotBlank(pageVO.getName())) {
            taskQuery.taskNameLike("%" + pageVO.getName() + "%");
        }
        if (pageVO.getBeginCreateTime() != null) {
            taskQuery.taskCreatedAfter(pageVO.getBeginCreateTime());
        }
        if (pageVO.getEndCreateTime() != null) {
            taskQuery.taskCreatedBefore(pageVO.getEndCreateTime());
        }
        // ????????????
        List<Task> tasks = taskQuery.listPage(PageUtils.getStart(pageVO), pageVO.getPageSize());
        if (CollUtil.isEmpty(tasks)) {
            return PageResult.empty(taskQuery.count());
        }

        // ?????? ProcessInstance Map
        Map<String, ProcessInstance> processInstanceMap = processInstanceService.getProcessInstanceMap(
                convertSet(tasks, Task::getProcessInstanceId));
        // ?????? User Map
        Map<Long, AdminUserRespDTO> userMap = adminUserApi.getUserMap(
                convertSet(processInstanceMap.values(), instance -> Long.valueOf(instance.getStartUserId())));
        // ????????????
        return new PageResult<>(BpmTaskConvert.INSTANCE.convertList1(tasks, processInstanceMap, userMap),
                taskQuery.count());
    }

    @Override
    public PageResult<BpmTaskDonePageItemRespVO> getDoneTaskPage(Long userId, BpmTaskDonePageReqVO pageVO) {
        // ??????????????????
        HistoricTaskInstanceQuery taskQuery = historyService.createHistoricTaskInstanceQuery()
                .finished() // ?????????
                .taskAssignee(String.valueOf(userId)) // ???????????????
                .orderByHistoricTaskInstanceEndTime().desc(); // ??????????????????
        if (StrUtil.isNotBlank(pageVO.getName())) {
            taskQuery.taskNameLike("%" + pageVO.getName() + "%");
        }
        if (pageVO.getBeginCreateTime() != null) {
            taskQuery.taskCreatedAfter(pageVO.getBeginCreateTime());
        }
        if (pageVO.getEndCreateTime() != null) {
            taskQuery.taskCreatedBefore(pageVO.getEndCreateTime());
        }
        // ????????????
        List<HistoricTaskInstance> tasks = taskQuery.listPage(PageUtils.getStart(pageVO), pageVO.getPageSize());
        if (CollUtil.isEmpty(tasks)) {
            return PageResult.empty(taskQuery.count());
        }

        // ?????? TaskExtDO Map
        List<BpmTaskExtDO> bpmTaskExtDOs = taskExtMapper.selectListByTaskIds(convertSet(tasks, HistoricTaskInstance::getId));
        Map<String, BpmTaskExtDO> bpmTaskExtDOMap = convertMap(bpmTaskExtDOs, BpmTaskExtDO::getTaskId);
        // ?????? ProcessInstance Map
        Map<String, HistoricProcessInstance> historicProcessInstanceMap = processInstanceService.getHistoricProcessInstanceMap(
                convertSet(tasks, HistoricTaskInstance::getProcessInstanceId));
        // ?????? User Map
        Map<Long, AdminUserRespDTO> userMap = adminUserApi.getUserMap(
                convertSet(historicProcessInstanceMap.values(), instance -> Long.valueOf(instance.getStartUserId())));
        // ????????????
        return new PageResult<>(BpmTaskConvert.INSTANCE.convertList2(tasks, bpmTaskExtDOMap, historicProcessInstanceMap, userMap),
                taskQuery.count());
    }

    @Override
    public void updateTaskAssignee(Long userId, BpmTaskUpdateAssigneeReqVO reqVO) {
        // ??????????????????
        Task task = getTask(reqVO.getId());
        if (task == null) {
            throw exception(TASK_COMPLETE_FAIL_NOT_EXISTS);
        }
        if (!ActivitiUtils.equals(task.getAssignee(), userId)) {
            throw exception(TASK_COMPLETE_FAIL_ASSIGN_NOT_SELF);
        }

        // ???????????????
        updateTaskAssignee(task.getId(), reqVO.getAssigneeUserId());
    }

    @Override
    public void updateTaskAssignee(String id, Long userId) {
        taskService.setAssignee(id, String.valueOf(userId));
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void approveTask(Long userId, BpmTaskApproveReqVO reqVO) {
        // ??????????????????
        Task task = getTask(reqVO.getId());
        if (task == null) {
            throw exception(TASK_COMPLETE_FAIL_NOT_EXISTS);
        }
        if (!ActivitiUtils.equals(task.getAssignee(), userId)) {
            throw exception(TASK_COMPLETE_FAIL_ASSIGN_NOT_SELF);
        }
        // ????????????????????????
        ProcessInstance instance = processInstanceService.getProcessInstance(task.getProcessInstanceId());
        if (instance == null) {
            throw exception(PROCESS_INSTANCE_NOT_EXISTS);
        }

        // ???????????????????????????
        taskService.complete(task.getId(), instance.getProcessVariables()); // TODO ?????????variables ?????????
        // ??????????????????????????????
        taskExtMapper.updateByTaskId(new BpmTaskExtDO().setTaskId(task.getId())
                .setResult(BpmProcessInstanceResultEnum.APPROVE.getResult()).setComment(reqVO.getComment()));

        // TODO ?????????????????????
//        taskService.addComment(task.getId(), task.getProcessInstanceId(), reqVO.getComment());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void rejectTask(Long userId, @Valid BpmTaskRejectReqVO reqVO) {
        // ??????????????????
        Task task = getTask(reqVO.getId());
        if (task == null) {
            throw exception(TASK_COMPLETE_FAIL_NOT_EXISTS);
        }
        if (!ActivitiUtils.equals(task.getAssignee(), userId)) {
            throw exception(TASK_COMPLETE_FAIL_ASSIGN_NOT_SELF);
        }
        // ????????????????????????
        ProcessInstance instance = processInstanceService.getProcessInstance(task.getProcessInstanceId());
        if (instance == null) {
            throw exception(PROCESS_INSTANCE_NOT_EXISTS);
        }

        // ??????????????????????????????
        processInstanceService.updateProcessInstanceExtReject(instance.getProcessInstanceId(), reqVO.getComment());

        // ?????????????????????????????????
        taskExtMapper.updateByTaskId(new BpmTaskExtDO().setTaskId(task.getId())
                .setResult(BpmProcessInstanceResultEnum.REJECT.getResult()).setComment(reqVO.getComment()));

        // TODO ?????????????????????
//        taskService.addComment(task.getId(), task.getProcessInstanceId(), reqVO.getComment());
    }

    private Task getTask(String id) {
        return taskService.createTaskQuery().taskId(id).singleResult();
    }

    // ========== Task ??????????????? ==========

    @Override
    public void createTaskExt(org.activiti.api.task.model.Task task) {
        // ?????? BpmTaskExtDO ??????
        BpmTaskExtDO taskExtDO = BpmTaskConvert.INSTANCE.convert(task)
                .setResult(BpmProcessInstanceResultEnum.PROCESS.getResult());
        taskExtMapper.insert(taskExtDO);
    }

    @Override
    public void updateTaskExt(org.activiti.api.task.model.Task task) {
        BpmTaskExtDO taskExtDO = BpmTaskConvert.INSTANCE.convert(task);
        taskExtMapper.updateByTaskId(taskExtDO);
    }

    @Override
    public void updateTaskExtAssign(org.activiti.api.task.model.Task task) {
        // ??????
        updateTaskExt(task);
        // ????????????????????? Activiti ??????????????????????????????????????????????????????????????????????????????????????? ProcessInstance?????????????????????????????????????????????????????????
        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                ProcessInstance processInstance = processInstanceService.getProcessInstance(task.getProcessInstanceId());
                AdminUserRespDTO startUser = adminUserApi.getUser(Long.valueOf(processInstance.getStartUserId()));
                messageService.sendMessageWhenTaskAssigned(BpmTaskConvert.INSTANCE.convert(processInstance, startUser, task));
            }
        });
    }

    @Override
    public void updateTaskExtCancel(org.activiti.api.task.model.Task task) {
        BpmTaskExtDO taskExtDO = BpmTaskConvert.INSTANCE.convert(task)
                .setEndTime(new Date()) // ?????? Task ????????????????????? endTime?????????????????????
                .setResult(BpmProcessInstanceResultEnum.CANCEL.getResult());
        taskExtMapper.updateByTaskId(taskExtDO);
    }

    @Override
    public void updateTaskExtComplete(org.activiti.api.task.model.Task task) {
        BpmTaskExtDO taskExtDO = BpmTaskConvert.INSTANCE.convert(task)
                .setEndTime(new Date()) // ?????????????????? task ??? completeData????????????????????????
                .setResult(BpmProcessInstanceResultEnum.APPROVE.getResult());
        taskExtMapper.updateByTaskId(taskExtDO);
    }

    @Override
    public List<BpmTaskExtDO> getTaskExtListByProcessInstanceId(String processInstanceId) {
        return taskExtMapper.selectListByProcessInstanceId(processInstanceId);
    }

}
