package com.ruoyi.admin.api.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.domain.entity.SysUser;
import com.ruoyi.common.mybatis.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper
public interface AdminUserMapper extends BaseMapperX<SysUser> {

    default List<SysUser> selectListByDeptIds(Collection<Long> deptIds) {
        return selectList(SysUser::getDeptId, deptIds);
    }

}

