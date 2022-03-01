package com.ruoyi.bpm.mapper.oa;

import com.ruoyi.common.pojo.PageResult;
import com.ruoyi.common.mybatis.mapper.BaseMapperX;
import com.ruoyi.common.mybatis.query.LambdaQueryWrapperX;
import com.ruoyi.bpm.controller.oa.vo.BpmOALeavePageReqVO;
import com.ruoyi.bpm.domain.oa.BpmOALeaveDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 请假申请 Mapper
 *
 * @author jason
 * @author 芋道源码
 */
@Mapper
public interface BpmOALeaveMapper extends BaseMapperX<BpmOALeaveDO> {

    default PageResult<BpmOALeaveDO> selectPage(Long userId, BpmOALeavePageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<BpmOALeaveDO>()
                .eqIfPresent(BpmOALeaveDO::getUserId, userId)
                .eqIfPresent(BpmOALeaveDO::getResult, reqVO.getResult())
                .eqIfPresent(BpmOALeaveDO::getType, reqVO.getType())
                .likeIfPresent(BpmOALeaveDO::getReason, reqVO.getReason())
                .betweenIfPresent(BpmOALeaveDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc(BpmOALeaveDO::getId));
    }

}
