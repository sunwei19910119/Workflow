package com.ruoyi.bpm.service.oa.listener;

import com.ruoyi.bpm.framework.event.BpmProcessInstanceResultEvent;
import com.ruoyi.bpm.framework.event.BpmProcessInstanceResultEventListener;
import com.ruoyi.bpm.service.oa.BpmOALeaveService;
import com.ruoyi.bpm.service.oa.BpmOALeaveServiceImpl;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * OA 请假单的结果的监听器实现类
 *
 * @author 芋道源码
 */
@Component
public class BpmOALeaveResultListener extends BpmProcessInstanceResultEventListener {

    @Resource
    private BpmOALeaveService leaveService;

    @Override
    protected String getProcessDefinitionKey() {
        return BpmOALeaveServiceImpl.PROCESS_KEY;
    }

    @Override
    protected void onEvent(BpmProcessInstanceResultEvent event) {
        leaveService.updateLeaveResult(Long.parseLong(event.getBusinessKey()), event.getResult());
    }

}
