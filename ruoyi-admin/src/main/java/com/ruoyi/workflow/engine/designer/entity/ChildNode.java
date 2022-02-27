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

public class ChildNode extends Node{

    private ChildProperties properties;
    //外层id
    private String outerNodeId;

    public ChildProperties getProperties() {
        return properties;
    }

    public void setProperties(ChildProperties properties) {
        this.properties = properties;
    }

    public String getOuterNodeId() {
        return outerNodeId;
    }

    public void setOuterNodeId(String outerNodeId) {
        this.outerNodeId = outerNodeId;
    }
}