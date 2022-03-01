package com.ruoyi.admin.api.mapper;

import com.ruoyi.admin.domain.SysPost;
import com.ruoyi.common.mybatis.mapper.BaseMapperX;
import org.apache.ibatis.annotations.Mapper;

import java.util.Collection;
import java.util.List;

@Mapper
public interface PostMapper extends BaseMapperX<SysPost> {

//    default List<PostDO> selectList(Collection<Long> ids, Collection<Integer> statuses) {
//        return selectList(new QueryWrapperX<PostDO>().inIfPresent("id", ids)
//                .inIfPresent("status", statuses));
//    }
//
//    default PageResult<PostDO> selectPage(PostPageReqVO reqVO) {
//        return selectPage(reqVO, new QueryWrapperX<PostDO>()
//                .likeIfPresent("code", reqVO.getCode())
//                .likeIfPresent("name", reqVO.getName())
//                .eqIfPresent("status", reqVO.getStatus()));
//    }
//
//    default List<PostDO> selectList(PostExportReqVO reqVO) {
//        return selectList(new QueryWrapperX<PostDO>()
//                .likeIfPresent("code", reqVO.getCode())
//                .likeIfPresent("name", reqVO.getName())
//                .eqIfPresent("status", reqVO.getStatus()));
//    }
//
//    default PostDO selectByName(String name) {
//        return selectOne(new QueryWrapper<PostDO>().eq("name", name));
//    }
//
//    default PostDO selectByCode(String code) {
//        return selectOne(new QueryWrapper<PostDO>().eq("code", code));
//    }

}
