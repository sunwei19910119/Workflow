package com.ruoyi.workflow.engine.service.impl;

import com.alibaba.druid.util.StringUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.util.StringUtil;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.workflow.engine.entity.FlowDesignerEntity;
import com.ruoyi.workflow.engine.mapper.FlowDesignerMapper;
import com.ruoyi.workflow.engine.model.flowDesigner.PaginationFlowDesigner;
import com.ruoyi.workflow.engine.service.FlowDesignerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


@Service
public class FlowDesignerServiceImpl extends ServiceImpl<FlowDesignerMapper, FlowDesignerEntity> implements FlowDesignerService {

    @Autowired
    private FlowDesignerService flowEngineService;

    @Override
    public List<FlowDesignerEntity> getList(PaginationFlowDesigner pagination) {
        QueryWrapper<FlowDesignerEntity> queryWrapper = new QueryWrapper<>();
        if (StringUtil.isNotEmpty(pagination.getKeyword())) {
            queryWrapper.lambda().like(FlowDesignerEntity::getFullName, pagination.getKeyword());
        }
        if (StringUtil.isNotEmpty(pagination.getEnabledMark())) {
            queryWrapper.lambda().like(FlowDesignerEntity::getEnabledMark, pagination.getEnabledMark());
        }
        if (StringUtil.isNotEmpty(pagination.getFullName())) {
            queryWrapper.lambda().like(FlowDesignerEntity::getFullName, pagination.getFullName());
        }
        queryWrapper.lambda().orderByAsc(FlowDesignerEntity::getSortCode);
        return this.list(queryWrapper);
    }

    @Override
    public FlowDesignerEntity getInfo(String id) throws ServiceException {
        QueryWrapper<FlowDesignerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(FlowDesignerEntity::getId, id);
        FlowDesignerEntity flowEngineEntity = this.getOne(queryWrapper);
        if (flowEngineEntity == null) {
            throw new ServiceException("未找到流程引擎");
        }
        return flowEngineEntity;
    }

    @Override
    public FlowDesignerEntity getInfoByEnCode(String enCode) throws ServiceException {
        QueryWrapper<FlowDesignerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(FlowDesignerEntity::getEnCode, enCode).eq(FlowDesignerEntity::getEnabledMark, 1);
        FlowDesignerEntity flowEngineEntity = this.getOne(queryWrapper);
        if (flowEngineEntity == null) {
            throw new ServiceException("未找到流程引擎");
        }
        return flowEngineEntity;
    }

    @Override
    public boolean isExistByFullName(String fullName, String id) {
        QueryWrapper<FlowDesignerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(FlowDesignerEntity::getFullName, fullName);
        if (!StringUtils.isEmpty(id)) {
            queryWrapper.lambda().ne(FlowDesignerEntity::getId, id);
        }
        return this.count(queryWrapper) > 0 ? true : false;
    }

    @Override
    public boolean isExistByEnCode(String enCode, String id) {
        QueryWrapper<FlowDesignerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(FlowDesignerEntity::getEnCode, enCode);
        if (!StringUtils.isEmpty(id)) {
            queryWrapper.lambda().ne(FlowDesignerEntity::getId, id);
        }
        return this.count(queryWrapper) > 0 ? true : false;
    }

    @Override
    public void delete(FlowDesignerEntity entity) {
        this.removeById(entity.getId());
    }

    @Override
    public boolean update(String id, FlowDesignerEntity entity) {
        entity.setId(id);
        entity.setLastModifyTime(new Date());
        SysUser user = SecurityUtils.getLoginUser().getUser();
        entity.setLastModifyUserId(""+user.getUserId());
        return this.updateById(entity);
    }

    @Override
    public void create(FlowDesignerEntity entity) {
        this.save(entity);
    }
}
