package com.ruoyi.admin.api.domain;

import com.ruoyi.common.core.domain.entity.SysDept;
import com.ruoyi.common.enums.CommonStatusEnum;
import lombok.Data;

/**
 * 部门 Response DTO
 *
 * @author 芋道源码
 */
@Data
public class DeptRespDTO {

    /**
     * 部门编号
     */
    private Long id;
    /**
     * 部门名称
     */
    private String name;
    /**
     * 父部门编号
     */
    private Long parentId;
    /**
     * 负责人的用户编号
     */
    private Long leaderUserId;
    /**
     * 部门状态
     *
     * 枚举 {@link CommonStatusEnum}
     */
    private Integer status;

    public void convert(SysDept dept) {
        id = dept.getDeptId();
        name = dept.getDeptName();
        parentId = dept.getParentId();
        leaderUserId = dept.getLeaderUserId();
        status = Integer.valueOf(dept.getStatus());
    }

}
