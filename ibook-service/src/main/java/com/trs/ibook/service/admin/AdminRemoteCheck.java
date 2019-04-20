package com.trs.ibook.service.admin;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.season.common.StrKit;
import com.trs.ibook.core.exception.IBookException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Title:
 * Description:
 * Copyright: 2017 北京拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company:北京拓尔思信息技术股份有限公司(TRS)
 * Project: hshop
 *
 * @author: Vincent
 * Create Time:2017/10/10 14:30
 */
@Component
public class AdminRemoteCheck {

    @Value("${casarte.admin.authServiceUrl}")
    private String authServiceUrl;
    @Value("${casarte.admin.operationCenter.moduleId}")
    private String moduleId;
    @Autowired
    private RestTemplate restTemplate;

    public boolean check(IDSUser user, String operKey) {
        if (StrKit.isEmpty(authServiceUrl)) {
            throw new IBookException("authServiceUrl为空，操作权限判断失败，请配置hshop.admin.authServiceUrl");
        }
        if (user == null) {
            throw new IBookException("用户对象为空，操作权限判断失败，请确认用户是否登录");
        }
        if (StrKit.isEmpty(operKey)) {
            throw new IBookException("operKey为空，操作权限判断失败，请确认已传入operateKey");
        }
        if (StrKit.isEmpty(moduleId)) {
            throw new IBookException("moduleId为空，操作权限判断失败，请确认已配置hshop.admin.moduleId");
        }
        String serviceUrl = authServiceUrl + "?serviceid=" + "{serviceId}" +
                "&methodName=" + "{methodName}" + "&OPRKEYS=" + "{porKeys}" +
                "&userName=" + "{userName}" + "&MODULEID=" + "{moduleId}" +
                "&CurrUserName=" + "{currUserName}";
        Map<String, Object> param = new HashMap<>();
        param.put("serviceId", "gov_right");
        param.put("methodName", "hasRight");
        param.put("porKeys", operKey);
        param.put("moduleId", moduleId);
        try {
            param.put("userName", URLEncoder.encode(user.getUserName(), "UTF-8"));
            param.put("currUserName", URLEncoder.encode(user.getUserName(), "UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new IBookException("调用WCM权限接口，编码用户名异常：" + user.getUserName());
        }
        ResponseEntity<String> entity = restTemplate.postForEntity(serviceUrl, null, String.class, param);
        if (!entity.getStatusCode().equals(HttpStatus.OK)) {
            throw new IBookException("[接口]调用WCM权限查询接口失败，CODE:" + entity.getStatusCode().value()
                    + "；信息：" + entity.getBody());
        }
        String result = entity.getBody();
        //解析数据
        if (StrKit.isEmpty(result)) {
            return false;
        }
        JSONObject operObject = JSON.parseObject(result);
        boolean isSuccess = operObject.getBoolean("ISSUCCESS");
        if (!isSuccess) {
            throw new IBookException("[接口]调用WCM权限查询接口失败，CODE:" + entity.getStatusCode().value()
                    + "；信息：" + entity.getBody());
        }
        String data = (String) operObject.get("DATA");
        return "true".equals(data);
    }
}
