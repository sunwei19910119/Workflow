package com.ruoyi.admin.api.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.ruoyi.admin.api.mapper.PostMapper;
import com.ruoyi.admin.api.service.PostApi;
import com.ruoyi.admin.domain.SysPost;
import com.ruoyi.admin.domain.server.Sys;
import com.ruoyi.common.enums.CommonStatusEnum;
import com.ruoyi.common.exception.ServiceException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static com.ruoyi.common.exception.util.ServiceExceptionUtil.exception;
import static com.ruoyi.common.utils.collection.CollectionUtils.convertMap;
/**
 * 岗位 API 实现类
 *
 * @author 芋道源码
 */
@Service
public class PostApiImpl implements PostApi {

    @Resource
    private PostMapper postMapper;


    @Override
    public void validPosts(Collection<Long> ids) {
        if (CollUtil.isEmpty(ids)) {
            return;
        }
        // 获得岗位信息
        List<SysPost> posts = postMapper.selectBatchIds(ids);
        Map<Long, SysPost> postMap = convertMap(posts, SysPost::getPostId);
        // 校验
        ids.forEach(id -> {
            SysPost post = postMap.get(id);
            if (post == null) {
                throw new ServiceException("岗位没找到");
            }
            if (!CommonStatusEnum.ENABLE.getStatus().equals(Integer.valueOf(post.getStatus()))) {
                throw new ServiceException("岗位已禁用");
            }
        });
    }
}
