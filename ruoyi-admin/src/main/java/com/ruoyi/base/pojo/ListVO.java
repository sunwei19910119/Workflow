package com.ruoyi.base.pojo;

import lombok.Data;

import java.util.List;

@Data
public class ListVO<T> {
    private List<T>  list;

}
