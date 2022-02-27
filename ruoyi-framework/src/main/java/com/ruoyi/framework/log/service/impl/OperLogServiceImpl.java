package com.ruoyi.framework.log.service.impl;

import com.ruoyi.framework.log.domain.OperLog;
import com.ruoyi.framework.log.mapper.OperLogMapper;
import com.ruoyi.framework.log.service.IOperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 操作日志 服务层处理
 * 
 * @author ruoyi
 */
@Service
public class OperLogServiceImpl implements IOperLogService
{
    @Autowired
    private OperLogMapper operLogMapper;

    /**
     * 新增操作日志
     * 
     * @param operLog 操作日志对象
     */
    @Override
    public void insertOperlog(OperLog operLog)
    {
        operLogMapper.insertOperlog(operLog);
    }

}
