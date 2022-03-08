package com.ruoyi.bpm.service.definition.impl;

import cn.hutool.core.lang.Assert;
import com.ruoyi.bpm.controller.definition.vo.form.BpmFormCreateReqVO;
import com.ruoyi.bpm.controller.definition.vo.form.BpmFormPageReqVO;
import com.ruoyi.bpm.controller.definition.vo.form.BpmFormUpdateReqVO;
import com.ruoyi.bpm.convert.definition.BpmFormConvert;
import com.ruoyi.bpm.domain.definition.BpmFormDO;
import com.ruoyi.bpm.mapper.definition.BpmFormMapper;
import com.ruoyi.bpm.enums.ErrorCodeConstants;
import com.ruoyi.bpm.service.definition.BpmFormService;
import com.ruoyi.bpm.service.definition.dto.BpmFormFieldRespDTO;
import com.ruoyi.common.exception.util.ServiceExceptionUtil;
import com.ruoyi.common.pojo.PageResult;
import com.ruoyi.common.utils.json.JsonUtils;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import java.util.*;

/**
 * 动态表单 Service 实现类
 *
 * @author 风里雾里
 */
@Service
@Validated
public class BpmFormServiceImpl implements BpmFormService {

    @Resource
    private BpmFormMapper formMapper;

    @Override
    public Long createForm(BpmFormCreateReqVO createReqVO) {
        this.checkFields(createReqVO.getFields());
        // 插入
        BpmFormDO form = BpmFormConvert.INSTANCE.convert(createReqVO);
        System.out.println(form);
        formMapper.insert(form);
        // 返回
        return form.getId();
    }

    @Override
    public void updateForm(BpmFormUpdateReqVO updateReqVO) {
        this.checkFields(updateReqVO.getFields());
        // 校验存在
        this.validateFormExists(updateReqVO.getId());
        // 更新
        BpmFormDO updateObj = BpmFormConvert.INSTANCE.convert(updateReqVO);
        formMapper.updateById(updateObj);
    }

    @Override
    public void deleteForm(Long id) {
        // 校验存在
        this.validateFormExists(id);
        // 删除
        formMapper.deleteById(id);
    }

    private void validateFormExists(Long id) {
        if (formMapper.selectById(id) == null) {
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.FORM_NOT_EXISTS);
        }
    }

    @Override
    public BpmFormDO getForm(Long id) {
        return formMapper.selectById(id);
    }

    @Override
    public List<BpmFormDO> getFormList() {
        return formMapper.selectList();
    }

    @Override
    public List<BpmFormDO> getFormList(Collection<Long> ids) {
        return formMapper.selectBatchIds(ids);
    }

    @Override
    public PageResult<BpmFormDO> getFormPage(BpmFormPageReqVO pageReqVO) {
        return formMapper.selectPage(pageReqVO);
    }

    /**
     * @author: SunWei
     * @date: 2022/3/8 15:58
     * @param:
     * @return:
     * @description: 校验 Field，避免 field 重复
     */
    private void checkFields(List<String> fields) {
        Set<String> fieldNames = new HashSet<>();
        for (String field : fields) {
            BpmFormFieldRespDTO fieldDTO = JsonUtils.parseObject(field, BpmFormFieldRespDTO.class);
            fieldNames.add(fieldDTO.getVModel());
        }
        if (fieldNames.size() != fields.size()){
            // 如果存在重复的字段名字，则报错
            throw ServiceExceptionUtil.exception(ErrorCodeConstants.FORM_FIELD_REPEAT);
        }

    }

}
