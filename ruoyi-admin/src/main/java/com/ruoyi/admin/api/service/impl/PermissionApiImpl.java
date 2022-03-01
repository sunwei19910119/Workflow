package com.ruoyi.admin.api.service.impl;

import com.ruoyi.admin.api.service.PermissionApi;
import com.ruoyi.admin.domain.SysUserRole;
import com.ruoyi.admin.api.mapper.UserRoleMapper;
import com.ruoyi.common.utils.collection.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.Set;

/**
 * 权限 API 实现类
 *
 * @author 芋道源码
 */
@Service
public class PermissionApiImpl implements PermissionApi {

    @Resource
    private UserRoleMapper userRoleMapper;

    @Override
    public Set<Long> getUserRoleIdListByRoleIds(Collection<Long> roleIds) {
        return CollectionUtils.convertSet(userRoleMapper.selectListByRoleIds(roleIds),
                SysUserRole::getUserId);
    }
}
