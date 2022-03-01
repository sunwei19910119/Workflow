package com.ruoyi.bpm.mapper.definition;

import com.ruoyi.common.pojo.PageResult;
import com.ruoyi.common.mybatis.mapper.BaseMapperX;
import com.ruoyi.common.mybatis.query.LambdaQueryWrapperX;
import com.ruoyi.bpm.controller.definition.vo.group.BpmUserGroupPageReqVO;
import com.ruoyi.bpm.domain.definition.BpmUserGroupDO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 用户组 Mapper
 *
 * @author 芋道源码
 */
@Mapper
public interface BpmUserGroupMapper extends BaseMapperX<BpmUserGroupDO> {

    default PageResult<BpmUserGroupDO> selectPage(BpmUserGroupPageReqVO reqVO) {
        return selectPage(reqVO, new LambdaQueryWrapperX<BpmUserGroupDO>()
                .likeIfPresent(BpmUserGroupDO::getName, reqVO.getName())
                .eqIfPresent(BpmUserGroupDO::getStatus, reqVO.getStatus())
                .betweenIfPresent(BpmUserGroupDO::getCreateTime, reqVO.getBeginCreateTime(), reqVO.getEndCreateTime())
                .orderByDesc(BpmUserGroupDO::getId));
    }

    default List<BpmUserGroupDO> selectListByStatus(Integer status) {
        return selectList(BpmUserGroupDO::getStatus, status);
    }

}
