package com.ruoyi.admin.api.convert;

import com.ruoyi.admin.api.domain.DictDataSimpleRespVO;
import com.ruoyi.common.core.domain.entity.SysDictData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.Collection;
import java.util.List;

@Mapper
public interface DictDataConvert {

    DictDataConvert INSTANCE = Mappers.getMapper(DictDataConvert.class);

    @Mappings({
            @Mapping(source = "dictValue",target = "value"),
            @Mapping(source = "dictLabel",target = "label"),
            @Mapping(source = "listClass",target = "colorType")
    })
    DictDataSimpleRespVO convert(SysDictData dictData);

    List<DictDataSimpleRespVO> converts(List<SysDictData> list);

}
