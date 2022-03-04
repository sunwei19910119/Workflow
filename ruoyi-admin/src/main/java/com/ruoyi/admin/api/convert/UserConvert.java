package com.ruoyi.admin.api.convert;

import com.ruoyi.admin.api.domain.AdminUserRespDTO;
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
    List<AdminUserRespDTO> convertList4(List<SysUser> users);

    Map<Long, AdminUserRespDTO> convertMap4(Map<Long, SysUser> map);

}
