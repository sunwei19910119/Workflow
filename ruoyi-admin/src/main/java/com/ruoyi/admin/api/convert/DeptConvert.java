package com.ruoyi.admin.api.convert;

import com.ruoyi.admin.api.domain.DeptRespDTO;
import com.ruoyi.common.core.domain.entity.SysDept;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;

@Mapper
public interface DeptConvert {

    DeptConvert INSTANCE = Mappers.getMapper(DeptConvert.class);

    @Mappings({
            @Mapping(source = "deptId",target = "id"),
            @Mapping(source = "deptName",target = "name")
    })
    DeptRespDTO convert03(SysDept bean);


    @Mappings({
            @Mapping(source = "deptId",target = "id"),
            @Mapping(source = "deptName",target = "name")
    })
    List<DeptRespDTO> convertList03(List<SysDept> list);

    @Mappings({
            @Mapping(source = "deptId",target = "id"),
            @Mapping(source = "deptName",target = "name")
    })
    Map<Long, DeptRespDTO> convertMap(Map<Long, SysDept> map);

}
