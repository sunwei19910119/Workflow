package com.ruoyi.bpm.mapper.task;

import com.ruoyi.common.pojo.PageResult;
import com.ruoyi.common.mybatis.mapper.BaseMapperX;
import com.ruoyi.common.mybatis.query.QueryWrapperX;
import com.ruoyi.bpm.controller.task.vo.instance.BpmProcessInstanceMyPageReqVO;
import com.ruoyi.bpm.domain.task.BpmProcessInstanceExtDO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BpmProcessInstanceExtMapper extends BaseMapperX<BpmProcessInstanceExtDO> {

    default PageResult<BpmProcessInstanceExtDO> selectPage(Long userId, BpmProcessInstanceMyPageReqVO reqVO) {
        return selectPage(reqVO, new QueryWrapperX<BpmProcessInstanceExtDO>()
                .eqIfPresent("start_user_id", userId)
                .likeIfPresent("name", reqVO.getName())
                .eqIfPresent("process_definition_id", reqVO.getProcessDefinitionId())
                .eqIfPresent("category", reqVO.getCategory())
                .eqIfPresent("status", reqVO.getStatus())
                .eqIfPresent("result", reqVO.getResult())
                .betweenIfPresent("create_time", reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc("id"));
    }

    default BpmProcessInstanceExtDO selectByProcessInstanceId(String processDefinitionId) {
        return selectOne("process_instance_id", processDefinitionId);
    }

    default void updateByProcessInstanceId(BpmProcessInstanceExtDO updateObj) {
        update(updateObj, new QueryWrapper<BpmProcessInstanceExtDO>()
                .eq("process_instance_id", updateObj.getProcessInstanceId()));
    }

}
