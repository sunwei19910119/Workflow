package com.ruoyi.framework.log.service.impl;
import com.ruoyi.framework.log.domain.Logininfor;
import com.ruoyi.framework.log.mapper.LogininforMapper;
import com.ruoyi.framework.log.service.ILogininforService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 系统访问日志情况信息 服务层处理
 * 
 * @author ruoyi
 */
@Service
public class LogininforServiceImpl implements ILogininforService
{

    @Autowired
    private LogininforMapper logininforMapper;

    /**
     * 新增系统登录日志
     * 
     * @param logininfor 访问日志对象
     */
    @Override
    public void insertLogininfor(Logininfor logininfor)
    {
        logininforMapper.insertLogininfor(logininfor);
    }
}
