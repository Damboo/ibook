package com.trs.ibook.core.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;

/**
 * Title: 自动配置类
 * Description: 自动配置类
 * Copyright: 2019 拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company: 拓尔思信息技术股份有限公司(TRS)
 * Project: ibook
 * Author: RayeGong
 * Create Time: 2019/03/12 15:30
 */
@Configuration
@PropertySources(value = @PropertySource("classpath:/config/ibook.properties"))
@ComponentScan(basePackages = "com.trs.ibook.core")
public class IBookAutoConfiguration {


}
