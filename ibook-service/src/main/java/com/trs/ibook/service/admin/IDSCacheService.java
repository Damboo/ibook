package com.trs.ibook.service.admin;

import com.season.data.ICache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 缓存登录管理员
 * by Vincent
 * on 2018-6-25 17:12:58
 */
@Service
class IDSCacheService {

    private final ICache iCache;
    @Autowired
    public IDSCacheService(ICache iCache) {
        this.iCache = iCache;
    }

    IDSUser getIDSUser(String ssotoken) {
        String idsUserKey = CacheKeyKit.getIDSUserKey(ssotoken);
        return iCache.get(idsUserKey);
    }

    void setIDSUser(String ssotoken, IDSUser idsUser) {
        String idsUserKey = CacheKeyKit.getIDSUserKey(ssotoken);
        iCache.set(idsUserKey, idsUser, CacheKeyKit.IDS_USER_EXPIRE_TIME);
    }

}
