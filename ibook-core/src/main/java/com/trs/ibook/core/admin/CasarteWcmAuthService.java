package com.trs.ibook.core.admin;

import com.trs.ibook.core.config.IBookAutoConfiguration;
import com.trs.ibook.core.exception.IBookException;
import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.util.WebUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * 承接权限验证的服务类
 */

@Component
public class CasarteWcmAuthService {
    private Logger logger = Logger.getLogger(CasarteWcmAuthService.class);
    @Autowired
    private IDSCacheService idsCacheService;
    @Autowired
    private IDSRemoteService idsRemoteService;
    @Autowired
    private IBookAutoConfiguration properties;
    @Autowired
    private AdminRemoteCheck adminRemoteCheck;

    /**
     * 获取当前登录用户，未登录时返回null
     */
    public IDSUser getLoginUser(HttpServletRequest request) {
        IDSUser idsUser;
        if (properties.isLocalVirtualUser()) {
            idsUser = idsRemoteService.virtualLocalUser();
        } else {
            idsUser = this.getIDSUser(request);
        }
        return idsUser;
    }

    /**
     * 直接校验是否登录，未登录时抛异常，由ExceptionHandler处理
     * warning:抛异常不属于在入口判断登录的正常方式,仅作用于小部分业务处理流程
     *
     * @param request
     */
    public void voidAuth(HttpServletRequest request) {
        IDSUser idsUser = this.getLoginUser(request);
        if (idsUser == null) {
            throw new IBookException("管理员未登录");
        }
    }

    /**
     * 校验是否有登录，未登录返回false
     *
     * @return true登录 false未登录
     */
    public boolean checkLogin(HttpServletRequest request) {
        IDSUser idsUser;
        if (properties.isLocalVirtualUser()) {
            idsUser = idsRemoteService.virtualLocalUser();
        } else {
            idsUser = this.getIDSUser(request);
        }
        return idsUser != null;
    }


    /**
     * 校验是否有登录,并且登录后是否有权限
     *
     * @param request
     * @return true登录 false未登录
     */
    public boolean checkOperkey(HttpServletRequest request, String operKey) {
        IDSUser idsUser;
        if (properties.isLocalVirtualUser()) {
            idsUser = idsRemoteService.virtualLocalUser();
        } else {
            idsUser = this.getIDSUser(request);
        }
        return idsUser != null && adminRemoteCheck.check(idsUser, operKey);
    }

    private IDSUser getIDSUser(HttpServletRequest request) {
        Cookie ssotokenCookie = WebUtils.getCookie(request, "trsidssdssotoken");
        String ssotoken = ssotokenCookie == null ? null : ssotokenCookie.getValue();
        logger.info("[print by tk]ssotoken:" + ssotoken);
        //如果不存在ssotoken，则代表用户未登录
        if (StringUtils.isBlank(ssotoken)) {
            return null;
        }
        //首先从缓存获取
        IDSUser idsUser = idsCacheService.getIDSUser(ssotoken);
        if (idsUser != null) {
            return idsUser;
        }
        logger.info("[print by tk]从缓存获取idsUser为null");
        // 从远程服务获取
        idsUser = idsRemoteService.getIDSAdminUser(ssotoken);
        // 获取到则设置到缓存
        if (idsUser != null) {
            idsCacheService.setIDSUser(ssotoken, idsUser);
            logger.info("[print by tk]从远程服务获取idsUser成功");
        }
        return idsUser;
    }

    /**
     * 获取WCM登录用户名,兼容旧平台
     */
    public String getWcmUsername(HttpServletRequest request) {
        IDSUser idsUser = getLoginUser(request);
        return idsUser != null ? idsUser.getUserName() : "";
    }
}
