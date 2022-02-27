package com.ruoyi.framework.log.service;


import com.ruoyi.framework.log.domain.Logininfor;

import java.util.List;

/**
 * 系统访问日志情况信息 服务层
 * 
 * @author ruoyi
 */
public interface ILogininforService
{
    /**
     * 新增系统登录日志
     * 
     * @param logininfor 访问日志对象
     */
    public void insertLogininfor(Logininfor logininfor);
}
