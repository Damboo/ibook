package com.trs.ibook.service.admin;

import com.alibaba.fastjson.JSON;
import com.season.common.SafeKit;
import com.season.common.StrKit;
import com.season.ids.util.IDSAPIClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Title: ids远程服务
 * Description:
 * Copyright: 2017 北京拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company:北京拓尔思信息技术股份有限公司(TRS)
 * Project: hshop
 * Author: Vincent
 * Create Time:2017/9/1 17:30
 */
@Service
class IDSRemoteService {

    private static final Logger logger = LoggerFactory.getLogger(IDSRemoteService.class);
    private static final String coSessionId = "7D94CE1EF02A86E4CFD0937329BF795E.jvm1";
    private static final String serviceUrl = "/service?idsServiceType=httpssoservice&serviceName=findUserBySSOID";


    @Value("${casarte.admin.idsAdminUrl:null}")
    private String idsAdminUrl;
    @Value("${casarte.admin.adminAppName:null}")
    private String adminAppName;
    @Value("${casarte.admin.adminAppKey:null}")
    private String adminAppKey;

    IDSUser getIDSAdminUser(String ssotoken) {
        String[] ssoTokens = ssotoken.split("_");
        if (ssoTokens.length != 2) {
            logger.error("获取管理员用户信息时的ssotoken格式错误，token：" + ssotoken);
            return null;
        }
        return getIDSUser(ssoTokens[0], idsAdminUrl, adminAppName, adminAppKey);
    }

    private IDSUser getIDSUser(String ssotoken, String idsUrl, String appName, String appKey) {
        IDSUser idsUser = null;
        String data = "ssoSessionId=" + ssotoken + "&" + "coSessionId=" + coSessionId;
        IDSAPIClient client = new IDSAPIClient(idsUrl + serviceUrl, appKey, "MD5", "json");
        // 从ids得到的明文响应结果
        String response;
        try {
            response = client.processor(appName, data);
        } catch (Exception e) {
            logger.error(e.getMessage());
            return null;
        }
        // 3、 解析IDS响应结果，根据响应结果执行应用的自身逻辑
        if (!StrKit.isEmpty(response)) {
            try {
                Map dataJson = JSON.parseObject(response, Map.class);
                Map dataList = (Map) dataJson.get("data");
                if (dataList == null) {
                    logger.error("ids返回用户信息失败:" + response);
                    return null;
                }
                Map user = (Map) dataList.get("user");
                idsUser = new IDSUser();
                idsUser.setUserName(SafeKit.getString(user.get("userName")));
                idsUser.setIdsUserId(SafeKit.getLong(user.get("userId")));
                idsUser.setLoginName(SafeKit.getString(user.get("loginAccountName")));
            } catch (Exception e) {
                logger.error("从ids接口获取用户信息失败", e);
                return null;
            }
        }
        return idsUser;
    }

    IDSUser virtualLocalUser() {
        IDSUser idsUser = new IDSUser();
        idsUser.setIdsUserId(300001432L);
        idsUser.setUserName("liumengfeitest");
        idsUser.setLoginName("H242R1507618304486");
        return idsUser;
    }
}
