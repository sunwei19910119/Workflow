package com.ruoyi.bpm.convert.oa;

import com.ruoyi.bpm.controller.oa.vo.BpmOALeaveCreateReqVO;
import com.ruoyi.bpm.controller.oa.vo.BpmOALeaveRespVO;
import com.ruoyi.bpm.domain.oa.BpmOALeaveDO;
import com.ruoyi.common.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 请假申请 Convert
 *
 * @author 芋艿
 */
@Mapper
public interface BpmOALeaveConvert {

    BpmOALeaveConvert INSTANCE = Mappers.getMapper(BpmOALeaveConvert.class);

    BpmOALeaveDO convert(BpmOALeaveCreateReqVO bean);

    BpmOALeaveRespVO convert(BpmOALeaveDO bean);

    List<BpmOALeaveRespVO> convertList(List<BpmOALeaveDO> list);

    PageResult<BpmOALeaveRespVO> convertPage(PageResult<BpmOALeaveDO> page);

}
