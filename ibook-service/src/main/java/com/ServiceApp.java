package com;

import com.season.core.SeasonBanner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Title: Service模块启动类
 * Description: Service模块启动类
 * Copyright: 2019 拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company: 拓尔思信息技术股份有限公司(TRS)
 * Project: ibook
 * Author: RayeGong
 * Create Time: 2019-03-11 17:30
 */
@SpringBootApplication
public class ServiceApp {

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(ServiceApp.class);
        app.setBanner(new SeasonBanner());
        app.run(args);
    }

}