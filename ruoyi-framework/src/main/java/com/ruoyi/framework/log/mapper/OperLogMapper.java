package com.ruoyi.framework.log.mapper;


import com.ruoyi.framework.log.domain.OperLog;

/**
 * 操作日志 数据层
 * 
 * @author ruoyi
 */
public interface OperLogMapper
{
    /**
     * 新增操作日志
     * 
     * @param operLog 操作日志对象
     */
    void insertOperlog(OperLog operLog);
}
