package com.ruoyi.admin.api.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.ruoyi.admin.api.convert.DeptConvert;
import com.ruoyi.admin.api.domain.DeptRespDTO;
import com.ruoyi.admin.api.service.DeptApi;
import com.ruoyi.admin.service.ISysDeptService;
import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.enums.CommonStatusEnum;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.collection.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * 部门 API 实现类
 *
 * @author 芋道源码
 */
@Service
public class DeptApiImpl implements DeptApi {

    @Resource
    private ISysDeptService deptService;

    @Override
    public DeptRespDTO getDept(Long id) {
        SysDept dept = deptService.selectDeptById(id);
        return DeptConvert.INSTANCE.convert03(dept);
    }

    @Override
    public List<DeptRespDTO> getDepts(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return Collections.emptyList();
        }
        List<SysDept> depts = deptService.selectBatchIds(ids);
        return DeptConvert.INSTANCE.convert03s(depts);
    }

    @Override
    public void validDepts(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return;
        }
        // 获得科室信息
        List<SysDept> depts = deptService.selectBatchIds(ids);
        Map<Long, SysDept> deptMap = CollectionUtils.convertMap(depts, SysDept::getDeptId);
        // 校验
        ids.forEach(id -> {
            SysDept dept = deptMap.get(id);
            if (dept == null) {
                throw new ServiceException("部门不存在");
            }
            if (!CommonStatusEnum.ENABLE.getStatus().equals(Integer.valueOf(dept.getStatus()))) {
                throw new ServiceException("部门已禁用");
            }
        });
    }

    @Override
    public Map<Long, DeptRespDTO> getDeptMap(Set<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return Collections.emptyMap();
        }
        List<SysDept> list = deptService.selectBatchIds(ids);
        Map<Long, SysDept> depts = CollectionUtils.convertMap(list, SysDept::getDeptId);
        Map<Long,DeptRespDTO> deptMap = new HashMap<>();
        for ( SysDept dept: depts.values()){
            DeptRespDTO deptRespDTO =  new DeptRespDTO();
            deptRespDTO.convert(dept);
            deptMap.put(dept.getDeptId(), deptRespDTO);
        }
        return deptMap;
    }

}
