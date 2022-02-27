package com.ruoyi.base.pojo;

import lombok.Data;

import java.util.List;

@Data
public class PageListVO<T> {
    private List<T> list;
    PaginationVO pagination;

}
