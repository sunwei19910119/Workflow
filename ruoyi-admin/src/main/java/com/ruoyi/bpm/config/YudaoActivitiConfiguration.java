package com.ruoyi.bpm.config;

import com.ruoyi.bpm.core.web.ActivitiWebFilter;
import com.ruoyi.bpm.enums.WebFilterOrderEnum;
import org.activiti.image.ProcessDiagramGenerator;
import org.activiti.image.impl.DefaultProcessDiagramGenerator;
import org.activiti.spring.boot.ProcessEngineConfigurationConfigurer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
public class YudaoActivitiConfiguration {

    /**
     * Activiti 流程图的生成器。目前管理后台的流程图 svg，通过它绘制生成。
     */
    @Bean
    public ProcessDiagramGenerator processDiagramGenerator() {
        return new DefaultProcessDiagramGenerator();
    }

    @Bean
    public FilterRegistrationBean<ActivitiWebFilter> activitiWebFilter() {
        FilterRegistrationBean<ActivitiWebFilter> registrationBean = new FilterRegistrationBean<>();
        registrationBean.setFilter(new ActivitiWebFilter());
        registrationBean.setOrder(WebFilterOrderEnum.ACTIVITI_FILTER);
        return registrationBean;
    }

    /**
     * ProcessEngineConfigurationConfigurer 实现类，设置事务管理器，保证 ACT_ 表和自己的表的事务一致性
     */
    @Bean
    public ProcessEngineConfigurationConfigurer processEngineConfigurationConfigurer(
            PlatformTransactionManager platformTransactionManager) {
        return processEngineConfiguration -> processEngineConfiguration.setTransactionManager(platformTransactionManager);
    }

}
