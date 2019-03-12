package com.trs.ibook.core.dto;

import com.season.common.JsonUtil;

import java.io.Serializable;

/**
 * Title: 数据传输对象超类
 * Description:
 * Copyright: 2017 北京拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company: 北京拓尔思信息技术股份有限公司(TRS)
 * Project: metadata
 * Author: cbb
 * Create Time: 2017-08-24 15:13
 */
public class AbstractDTO implements Serializable {

    @Override
    public String toString() {
        return JsonUtil.toJSONString(this);
    }

}
