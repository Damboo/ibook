package com.trs.ibook.service.pojo;

import com.season.orm.dao.annotation.Column;
import com.season.orm.dao.annotation.TableInfo;
import com.season.orm.dao.annotation.Transient;
import com.season.orm.dao.dialect.constant.MySqlTypeConst;
import com.trs.ibook.core.pojo.AbstractPOJO;

import java.util.Date;

import static com.trs.ibook.service.example.BookInfoExample.*;

/**
 * Title: 电子书信息表实体类
 * Description: 电子书信息表实体类
 * Copyright: 2019 拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company: 拓尔思信息技术股份有限公司(TRS)
 * Project: ibook
 * Author: RayeGong
 * Create Time: 2019-03-13 12:00
 */
@TableInfo(tableName = BookInfo.TABLE_NAME, pkName = "id")
public class BookInfo extends AbstractPOJO {

    @Transient
    public static final String TABLE_NAME = "ibook_book_info";

    @Column(length = 10, defaultValue = "", autoIncrement = true, comment = N_ID)
    private Integer id;

    @Column(length = 200, comment = N_TITLENAME)
    private String titleName;

    @Column(type = MySqlTypeConst.TEXT, length = 1000, comment = N_INTRODUCTION)
    private String introduction;

    @Column(length = 100, comment = N_AUTHOR)
    private String author;

    @Column(length = 100, comment = N_LABELCATEGORY)
    private String labelCategory;

    @Column(length = 10, comment = N_PERIODICAL)
    private Integer periodical;

    @Column(length = 10, comment = N_SITEID)
    private Integer siteId;

    @Column(type = MySqlTypeConst.SMALLINT, length = 1, notNull = true, comment = N_STATUS)
    private Integer status;

    @Column(notNull = true, comment = N_CREATETIME)
    private Date createTime;

    @Column(length = 20, comment = N_CREATEUSERID)
    private Long createUserId;

    @Column(type = MySqlTypeConst.SMALLINT, length = 1, notNull = true, comment = N_ISDELETE)
    private Integer isDelete;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitleName() {
        return titleName;
    }

    public void setTitleName(String titleName) {
        this.titleName = titleName;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLabelCategory() {
        return labelCategory;
    }

    public void setLabelCategory(String labelCategory) {
        this.labelCategory = labelCategory;
    }

    public Integer getPeriodical() {
        return periodical;
    }

    public void setPeriodical(Integer periodical) {
        this.periodical = periodical;
    }

    public Integer getSiteId() {
        return siteId;
    }

    public void setSiteId(Integer siteId) {
        this.siteId = siteId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Long createUserId) {
        this.createUserId = createUserId;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

}
