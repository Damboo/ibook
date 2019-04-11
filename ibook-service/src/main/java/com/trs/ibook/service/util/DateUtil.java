package com.trs.ibook.service.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Title:
 * Description:日期工具相关
 * Copyright: 2019 北京拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company:北京拓尔思信息技术股份有限公司(TRS)
 * Project: ibook
 * Author: KylerTien
 * Create Time:19-4-10 14:35
 */
public class DateUtil {

    /**
     * 获取当前日期的字符串
     * yyyyMMdd
     */
    public static String getTodayStr() {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
        return format.format(new Date());
    }
}
