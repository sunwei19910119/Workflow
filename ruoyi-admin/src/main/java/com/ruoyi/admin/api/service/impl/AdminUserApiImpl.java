package com.ruoyi.admin.api.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.ruoyi.admin.api.convert.UserConvert;
import com.ruoyi.admin.api.domain.AdminUserRespDTO;
import com.ruoyi.admin.api.service.AdminUserApi;
import com.ruoyi.admin.service.ISysUserService;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.enums.CommonStatusEnum;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.collection.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Admin 用户 API 实现类
 *
 * @author 芋道源码
 */
@Service
public class AdminUserApiImpl implements AdminUserApi {

    @Resource
    private ISysUserService userService;

    @Override
    public AdminUserRespDTO getUser(Long id) {
        SysUser user = userService.selectUserById(id);
        return UserConvert.INSTANCE.convert4(user);
    }

    @Override
    public List<AdminUserRespDTO> getUsersByDeptIds(Collection<Long> deptIds) {
        if (CollUtil.isEmpty(deptIds)) {
            return Collections.emptyList();
        }
        List<SysUser> users = userService.selectListByDeptIds(deptIds);
        return UserConvert.INSTANCE.convertList4(users);
    }

    @Override
    public List<AdminUserRespDTO> getUsersByPostIds(Collection<Long> postIds) {
        if (CollUtil.isEmpty(postIds)) {
            return Collections.emptyList();
        }
        // 过滤不符合条件的
        //TODO 待校验
        List<SysUser> users = userService.selectListByPostIds(postIds);
        return UserConvert.INSTANCE.convertList4(users);
    }

    @Override
    public Map<Long, AdminUserRespDTO> getUserMap(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return new HashMap<>();
        }
        List<SysUser> users = userService.selectBatchIds(ids);
        Map<Long, SysUser> userMap = CollectionUtils.convertMap( users , SysUser::getUserId);
        return UserConvert.INSTANCE.convertMap4(userMap);
    }

    @Override
    public void validUsers(Set<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return;
        }
        // 获得岗位信息
        List<SysUser> users = userService.selectBatchIds(ids);
        Map<Long, SysUser> userMap = CollectionUtils.convertMap(users, SysUser::getUserId);
        // 校验
        ids.forEach(id -> {
            SysUser user = userMap.get(id);
            if (user == null) {
                throw new ServiceException("用户不存在");
            }
            if (!CommonStatusEnum.ENABLE.getStatus().equals(Integer.valueOf(user.getStatus()))) {
                throw new ServiceException("用户已禁用");
            }
        });
    }

}
