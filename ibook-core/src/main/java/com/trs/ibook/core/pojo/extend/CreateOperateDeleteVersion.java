package com.trs.ibook.core.pojo.extend;

/**
 * Title:逻辑删除+创建人&创建日期+操作人&操作日期+版本号
 * Description:
 * Copyright: 2017 北京拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company:北京拓尔思信息技术股份有限公司(TRS)
 * Project: season
 * Author: cbb
 * Create Time:2017/5/26 15:02
 */
public interface CreateOperateDeleteVersion extends CreateByDate,OperateByDate,IsDelete,Version{
}
