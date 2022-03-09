package com.ruoyi.admin.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DeptSimpleVO {

    /** 部门ID */
    private Long deptId;

    /** 父部门ID */
    private Long parentId;

    /** 部门名称 */
    private String deptName;
}
