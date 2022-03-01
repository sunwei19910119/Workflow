package com.ruoyi.bpm.mapper.definition;

import com.ruoyi.common.mybatis.mapper.BaseMapperX;
import com.ruoyi.common.pojo.PageResult;
import com.ruoyi.common.mybatis.query.QueryWrapperX;
import com.ruoyi.bpm.controller.definition.vo.form.BpmFormPageReqVO;
import com.ruoyi.bpm.domain.definition.BpmFormDO;
import org.apache.ibatis.annotations.Mapper;

/**
 * 动态表单 Mapper
 *
 * @author 风里雾里
 */
@Mapper
public interface BpmFormMapper extends BaseMapperX<BpmFormDO> {

    default PageResult<BpmFormDO> selectPage(BpmFormPageReqVO reqVO) {
        return selectPage(reqVO, new QueryWrapperX<BpmFormDO>()
                .likeIfPresent("name", reqVO.getName())
                .orderByDesc("id"));
    }

}
