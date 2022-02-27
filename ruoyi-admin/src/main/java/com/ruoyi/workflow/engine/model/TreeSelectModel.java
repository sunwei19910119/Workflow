package com.ruoyi.workflow.engine.model;

import lombok.Data;

import java.util.List;

@Data
public class TreeSelectModel {
     private String id;
     private String parentId;
     private String name;
     private Boolean hasChildren;
     private String type;
     private String icon;
     private List<TreeSelectModel> children;
}
