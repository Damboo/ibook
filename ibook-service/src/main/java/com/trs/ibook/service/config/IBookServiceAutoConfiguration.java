package com.trs.ibook.service.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.*;

import java.io.IOException;

/**
 * Title: Service模块自动配置类
 * Description: Service模块自动配置类
 * Copyright: 2019 拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company: 拓尔思信息技术股份有限公司(TRS)
 * Project: ibook
 * Author: RayeGong
 * Create Time: 2019-03-12 11:30
 */
@Configuration
@PropertySources(value = @PropertySource("classpath:/config/ibookService.properties"))
@ComponentScan(basePackages = "com.trs.ibook.service")
public class IBookServiceAutoConfiguration {

    /**
     * 默认上下文，实际项目中请自己实现IBookServiceContext
     */
    @Bean
    @ConditionalOnMissingBean(IBookServiceContext.class)
    public IBookServiceContext IBookServiceContext() throws IOException {
        return new IBookServiceContext() {
            @Override
            public String getCurrentOperatorId() {
                return "admin";
            }
        };
    }

}
