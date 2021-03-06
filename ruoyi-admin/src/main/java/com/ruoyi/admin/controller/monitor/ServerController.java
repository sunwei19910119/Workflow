package com.ruoyi.admin.controller.monitor;

import com.ruoyi.admin.domain.Server;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.pojo.CommonResult;

/**
 * 服务器监控
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/monitor/server")
public class ServerController
{
    @PreAuthorize("@ss.hasPermi('monitor:server:list')")
    @GetMapping()
    public CommonResult getInfo() throws Exception
    {
        Server server = new Server();
        server.copyTo();
        return CommonResult.success(server);
    }
}
