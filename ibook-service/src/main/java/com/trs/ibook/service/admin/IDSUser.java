package com.trs.ibook.service.admin;

/**
 * 管理员信息
 * by Vincent
 * on 2018-6-25 17:25:01
 */
public class IDSUser {

    private Long idsUserId;
    private String userName;
    private String loginName;

    public Long getIdsUserId() {
        return idsUserId;
    }

    public void setIdsUserId(Long idsUserId) {
        this.idsUserId = idsUserId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
}
