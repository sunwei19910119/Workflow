package com.ruoyi.bpm.convert.definition;

import com.ruoyi.bpm.controller.definition.vo.form.BpmFormCreateReqVO;
import com.ruoyi.bpm.controller.definition.vo.form.BpmFormRespVO;
import com.ruoyi.bpm.controller.definition.vo.form.BpmFormSimpleRespVO;
import com.ruoyi.bpm.controller.definition.vo.form.BpmFormUpdateReqVO;
import com.ruoyi.bpm.domain.definition.BpmFormDO;
import com.ruoyi.common.pojo.PageResult;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

/**
 * 动态表单 Convert
 *
 * @author 芋艿
 */
@Mapper
public interface BpmFormConvert {

    BpmFormConvert INSTANCE = Mappers.getMapper(BpmFormConvert.class);

    BpmFormDO convert(BpmFormCreateReqVO bean);

    BpmFormDO convert(BpmFormUpdateReqVO bean);

    BpmFormRespVO convert(BpmFormDO bean);

    List<BpmFormSimpleRespVO> convertList2(List<BpmFormDO> list);

    PageResult<BpmFormRespVO> convertPage(PageResult<BpmFormDO> page);

}
