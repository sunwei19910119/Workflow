package com.ruoyi.admin.api.mapper;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.mybatis.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@Mapper
public interface DictDataMapper extends BaseMapperX<SysDictData> {

    default List<SysDictData> selectByDictType(String dictType) {
        return selectList(new LambdaQueryWrapper<SysDictData>().eq(SysDictData::getDictType, dictType));
    }
}
