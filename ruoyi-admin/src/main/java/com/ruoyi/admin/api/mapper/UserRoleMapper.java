package com.ruoyi.admin.api.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ruoyi.admin.domain.SysUserRole;
import com.ruoyi.common.mybatis.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Mapper
public interface UserRoleMapper extends BaseMapperX<SysUserRole> {

    default List<SysUserRole> selectListByRoleIds(Collection<Long> roleIds) {
        return selectList(SysUserRole::getRoleId, roleIds);
    }
}
