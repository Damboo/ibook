package com.trs.ibook.service.pojo;

import com.season.orm.dao.annotation.Column;
import com.season.orm.dao.annotation.TableInfo;
import com.season.orm.dao.annotation.Transient;
import com.season.orm.dao.dialect.constant.MySqlTypeConst;
import com.trs.ibook.core.pojo.AbstractPOJO;

import java.util.Date;

import static com.trs.ibook.service.example.BookCatalogExample.*;

/**
 * Title: 电子书目录表实体类
 * Description: 电子书目录表实体类
 * Copyright: 2019 拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company: 拓尔思信息技术股份有限公司(TRS)
 * Project: ibook
 * Author: RayeGong
 * Create Time: 2019-03-13 12:00
 */
@TableInfo(tableName = BookCatalog.TABLE_NAME, pkName = "id")
public class BookCatalog extends AbstractPOJO {

    @Transient
    public static final String TABLE_NAME = "ibook_book_catalog";

    @Column(length = 10, defaultValue = "", autoIncrement = true, comment = N_ID)
    private Integer id;

    @Column(length = 10, defaultValue = "0", comment = N_PARENTID)
    private Integer parentId;

    @Column(length = 10, notNull = true, comment = N_BOOKID)
    private Integer bookId;

    @Column(length = 200, comment = N_TITLENAME)
    private String titleName;

    @Column(type = MySqlTypeConst.TEXT, length = 1000, comment = N_INTRODUCTION)
    private String introduction;

    @Column(length = 10, comment = N_PAGEINDEX)
    private Integer pageIndex;

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

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
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

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
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
