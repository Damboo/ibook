package com.trs.ibook.core.admin;

import com.trs.ibook.core.exception.IBookException;
import org.springframework.util.Assert;

/**
 * 缓存管理员登录信息的key生成工具
 * by Vincent
 * on 2018-6-25 17:11:45
 */
class CacheKeyKit {

    private CacheKeyKit() {
        throw new IBookException("Illegal Status : Kit Class");
    }
    static final int IDS_USER_EXPIRE_TIME = 60 * 30;
    static String getIDSUserKey(String ssoToken) {
        Assert.notNull(ssoToken, "");
        return "iBook_wcm_user_" + ssoToken;
    }
}
