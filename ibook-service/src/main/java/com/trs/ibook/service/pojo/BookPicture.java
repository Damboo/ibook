package com.trs.ibook.service.pojo;

import com.season.orm.dao.annotation.Column;
import com.season.orm.dao.annotation.TableInfo;
import com.season.orm.dao.annotation.Transient;
import com.season.orm.dao.dialect.constant.MySqlTypeConst;
import com.trs.ibook.core.pojo.AbstractPOJO;

import java.util.Date;

import static com.trs.ibook.service.example.BookPictureExample.*;

/**
 * Title: 电子书图片表实体类
 * Description: 电子书图片表实体类
 * Copyright: 2019 拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company: 拓尔思信息技术股份有限公司(TRS)
 * Project: ibook
 * Author: RayeGong
 * Create Time: 2019-03-13 12:00
 */
@TableInfo(tableName = BookPicture.TABLE_NAME, pkName = "id")
public class BookPicture extends AbstractPOJO {

    @Transient
    static final String TABLE_NAME = "ibook_book_picture";

    @Column(length = 10, defaultValue = "", autoIncrement = true, comment = N_ID)
    private Integer id;

    @Column(length = 10, notNull = true, comment = N_BOOKID)
    private Integer bookId;

    @Column(length = 255, comment = N_PICURL)
    private String picUrl;

    @Column(length = 10, comment = N_PAGEINDEX)
    private Integer pageIndex;

    @Column(length = 10, comment = N_SERIALNO)
    private Integer serialNo;

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

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getPicUrl() {
        return picUrl;
    }

    public void setPicUrl(String picUrl) {
        this.picUrl = picUrl;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(Integer serialNo) {
        this.serialNo = serialNo;
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
