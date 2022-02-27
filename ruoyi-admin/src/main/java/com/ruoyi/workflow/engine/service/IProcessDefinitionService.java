package com.ruoyi.workflow.engine.service;

import com.github.pagehelper.Page;
import com.ruoyi.workflow.engine.entity.ProcessDefinition;

public interface IProcessDefinitionService {

    Page<ProcessDefinition> listProcessDefinition(ProcessDefinition processDefinition);
    void deployProcessDefinition(String filePath);
    void deployXMLProcessDefinition(String processName, String xml);
    int deleteProcessDeploymentByIds(String deploymentIds) throws Exception;
    void suspendOrActiveDefinition(String id, String suspendState);

}
