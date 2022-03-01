package com.ruoyi.bpm.core.web;

import com.ruoyi.admin.security.util.SecurityFrameworkUtils;
import com.ruoyi.bpm.core.util.ActivitiUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Activiti Web 过滤器，将 userId 设置到 {@link org.activiti.engine.impl.identity.Authentication} 中
 *
 * @author 芋道源码
 */
public class ActivitiWebFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {
        try {
            // 设置工作流的用户
            Long userId = SecurityFrameworkUtils.getLoginUserId();
            if (userId != null) {
                ActivitiUtils.setAuthenticatedUserId(userId);
            }
            // 过滤
            chain.doFilter(request, response);
        } finally {
            // 清理
            ActivitiUtils.clearAuthenticatedUserId();
        }
    }

}
