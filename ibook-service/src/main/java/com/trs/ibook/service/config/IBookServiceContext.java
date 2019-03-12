package com.trs.ibook.service.config;

/**
 * Title: Service模块上下文抽象类
 * Description: Service模块上下文抽象类
 * Copyright: 2019 拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company: 拓尔思信息技术股份有限公司(TRS)
 * Project: ibook
 * Author: RayeGong
 * Create Time: 2019-03-12 11:35
 */
public abstract class IBookServiceContext {

    /**
     * 获取当前操作员id
     * @return
     */
    public abstract String getCurrentOperatorId();

}
