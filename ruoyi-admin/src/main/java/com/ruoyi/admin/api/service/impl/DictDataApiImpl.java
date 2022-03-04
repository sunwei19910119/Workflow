package com.ruoyi.admin.api.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.ruoyi.admin.api.service.DictDataApi;
import com.ruoyi.admin.mapper.SysDictDataMapper;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.enums.CommonStatusEnum;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.collection.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * 字典数据 API 实现类
 *
 * @author 芋道源码
 */
@Service
public class DictDataApiImpl implements DictDataApi {


    @Resource
    SysDictDataMapper dictDataMapper;

    @Override
    public void validDictDatas(String dictType, Collection<String> values) {
        if (CollUtil.isEmpty(values)) {
            return;
        }
        List<SysDictData> list = dictDataMapper.selectDictDataByType(dictType);
        Map<Long, SysDictData> dictDataMap = CollectionUtils.convertMap(list, SysDictData::getDictCode);

        // 校验
        values.forEach(value -> {
            SysDictData dictData = dictDataMap.get(value);
            if (dictData == null) {
                throw new ServiceException("字典数据不存在");
            }
            if (!CommonStatusEnum.ENABLE.getStatus().equals(Integer.valueOf(dictData.getStatus()))) {
                throw new ServiceException("字典数据已禁用");
            }
        });
    }

}
