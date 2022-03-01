package com.ruoyi.bpm.mapper.task;

import com.ruoyi.common.mybatis.mapper.BaseMapperX;
import com.ruoyi.bpm.domain.task.BpmTaskExtDO;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper
public interface BpmTaskExtMapper extends BaseMapperX<BpmTaskExtDO> {

    default void updateByTaskId(BpmTaskExtDO entity) {
        update(entity, new LambdaQueryWrapper<BpmTaskExtDO>().eq(BpmTaskExtDO::getTaskId, entity.getTaskId()));
    }

    default List<BpmTaskExtDO> selectListByTaskIds(Collection<String> taskIds) {
        return selectList(BpmTaskExtDO::getTaskId, taskIds);
    }

    default List<BpmTaskExtDO> selectListByProcessInstanceId(String processInstanceId) {
        return selectList("process_instance_id", processInstanceId);
    }
}
