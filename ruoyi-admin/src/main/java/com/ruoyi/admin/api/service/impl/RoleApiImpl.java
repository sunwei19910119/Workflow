package com.ruoyi.admin.api.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.ruoyi.admin.api.mapper.RoleMapper;
import com.ruoyi.admin.api.service.RoleApi;
import com.ruoyi.admin.service.ISysRoleService;
import com.ruoyi.common.core.domain.entity.SysRole;
import com.ruoyi.common.enums.CommonStatusEnum;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.collection.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.ruoyi.common.exception.util.ServiceExceptionUtil.exception;

/**
 * 角色 API 实现类
 *
 * @author 芋道源码
 */
@Service
public class RoleApiImpl implements RoleApi {

    @Resource
    private ISysRoleService roleService;

    @Autowired
    private RoleMapper roleMapper;

    @Override
    public void validRoles(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return;
        }
        // 获得角色信息
        List<SysRole> roles = roleMapper.selectBatchIds(ids);
        Map<Long, SysRole> roleMap = CollectionUtils.convertMap(roles, SysRole::getRoleId);
        // 校验
        ids.forEach(id -> {
            SysRole role = roleMap.get(id);
            if (role == null) {
                throw new ServiceException("角色不存在");
            }
            if (!CommonStatusEnum.ENABLE.getStatus().equals(Integer.valueOf(role.getStatus()))) {
                throw new ServiceException("角色已禁用");
            }
        });
    }
}
