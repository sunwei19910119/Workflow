package com.ruoyi.admin.api.convert;

import com.ruoyi.admin.api.domain.AdminUserRespDTO;
import com.ruoyi.admin.api.domain.UserSimpleRespVO;
import com.ruoyi.common.core.domain.entity.SysUser;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserConvert {

    UserConvert INSTANCE = Mappers.getMapper(UserConvert.class);


    @Mappings({
            @Mapping(source = "userId",target = "id"),
            @Mapping(source = "nickName",target = "nickname")
    })
    AdminUserRespDTO convert4(SysUser bean);

    @Mappings({
            @Mapping(source = "userId",target = "id"),
            @Mapping(source = "nickName",target = "nickname")
    })
    UserSimpleRespVO convert(SysUser bean);

    List<AdminUserRespDTO> convert4s(List<SysUser> users);

    Map<Long, AdminUserRespDTO> convertMap4(Map<Long, SysUser> map);

    //循环调用convert方法
    List<UserSimpleRespVO> converts(List<SysUser> list);

}
