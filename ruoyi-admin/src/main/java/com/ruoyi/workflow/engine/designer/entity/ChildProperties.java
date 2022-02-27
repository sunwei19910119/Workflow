/**
 * Copyright 肇新智慧物业管理系统
 *
 * Licensed under AGPL开源协议
 *
 * gitee：https://gitee.com/fanhuibin1/zhaoxinpms
 * website：http://pms.zhaoxinms.com  wx： zhaoxinms
 *
 */
package com.ruoyi.workflow.engine.designer.entity;

import com.ruoyi.workflow.engine.model.TreeSelectModel;

import java.util.List;

public class ChildProperties {

    private String title;
    private List<TreeSelectModel> approvers;
    private List<TreeSelectModel> approverRoles;
    private String assigneeType;
    private String optionalMultiUser;
    private String expression;
    private List<FormOperate> formOperates;
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public List<TreeSelectModel> getApprovers() {
        return approvers;
    }
    public void setApprovers(List<TreeSelectModel> approvers) {
        this.approvers = approvers;
    }
    public List<TreeSelectModel> getApproverRoles() {
        return approverRoles;
    }
    public void setApproverRoles(List<TreeSelectModel> approverRoles) {
        this.approverRoles = approverRoles;
    }
    public String getAssigneeType() {
        return assigneeType;
    }
    public void setAssigneeType(String assigneeType) {
        this.assigneeType = assigneeType;
    }
    public String getOptionalMultiUser() {
        return optionalMultiUser;
    }
    public void setOptionalMultiUser(String optionalMultiUser) {
        this.optionalMultiUser = optionalMultiUser;
    }
    public String getExpression() {
        return expression;
    }
    public void setExpression(String expression) {
        this.expression = expression;
    }
    public List<FormOperate> getFormOperates() {
        return formOperates;
    }
    public void setFormOperates(List<FormOperate> formOperates) {
        this.formOperates = formOperates;
    }
}