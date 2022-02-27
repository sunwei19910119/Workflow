package com.ruoyi.framework.log.mapper;


import com.ruoyi.framework.log.domain.Logininfor;

/**
 * 系统访问日志情况信息 数据层
 * 
 * @author ruoyi
 */
public interface LogininforMapper
{
    /**
     * 新增系统登录日志
     * 
     * @param logininfor 访问日志对象
     */
    void insertLogininfor(Logininfor logininfor);

}
