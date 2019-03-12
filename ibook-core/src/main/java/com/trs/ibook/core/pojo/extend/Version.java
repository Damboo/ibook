package com.trs.ibook.core.pojo.extend;

/**
 * Title: 是否乐观锁版本接口
 * Description:
 * Copyright: 2017 北京拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company:北京拓尔思信息技术股份有限公司(TRS)
 * Project: season
 * Author: cbb
 * Create Time:2017/5/25 16:58
 */
public interface Version {

    Integer getVersion();

    void setVersion(Integer version);

}
