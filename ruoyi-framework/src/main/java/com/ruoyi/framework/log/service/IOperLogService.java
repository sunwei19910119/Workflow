package com.ruoyi.framework.log.service;

import com.ruoyi.framework.log.domain.OperLog;

import java.util.List;

/**
 * 操作日志 服务层
 * 
 * @author ruoyi
 */
public interface IOperLogService
{
    /**
     * 新增操作日志
     * 
     * @param operLog 操作日志对象
     */
    public void insertOperlog(OperLog operLog);
}
