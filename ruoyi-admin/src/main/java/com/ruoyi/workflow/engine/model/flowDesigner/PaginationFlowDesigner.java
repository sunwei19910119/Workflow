package com.ruoyi.workflow.engine.model.flowDesigner;

import com.ruoyi.base.vo.Pagination;
import lombok.Data;

@Data
public class PaginationFlowDesigner extends Pagination {
    private String enabledMark;
    private String fullName;
}
