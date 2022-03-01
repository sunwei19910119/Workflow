package com.ruoyi.bpm.convert.definition;

import java.util.*;

import com.ruoyi.bpm.controller.definition.vo.group.BpmUserGroupCreateReqVO;
import com.ruoyi.bpm.controller.definition.vo.group.BpmUserGroupRespVO;
import com.ruoyi.bpm.controller.definition.vo.group.BpmUserGroupUpdateReqVO;
import com.ruoyi.bpm.domain.definition.BpmUserGroupDO;
import com.ruoyi.common.pojo.PageResult;

import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

/**
 * 用户组 Convert
 *
 * @author 芋道源码
 */
@Mapper
public interface BpmUserGroupConvert {

    BpmUserGroupConvert INSTANCE = Mappers.getMapper(BpmUserGroupConvert.class);

    BpmUserGroupDO convert(BpmUserGroupCreateReqVO bean);

    BpmUserGroupDO convert(BpmUserGroupUpdateReqVO bean);

    BpmUserGroupRespVO convert(BpmUserGroupDO bean);

    List<BpmUserGroupRespVO> convertList(List<BpmUserGroupDO> list);

    PageResult<BpmUserGroupRespVO> convertPage(PageResult<BpmUserGroupDO> page);

    @Named("convertList2")
    List<BpmUserGroupRespVO> convertList2(List<BpmUserGroupDO> list);

}
