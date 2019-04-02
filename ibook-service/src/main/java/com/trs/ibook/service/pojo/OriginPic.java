package com.trs.ibook.service.pojo;

import com.season.orm.dao.annotation.Column;
import com.season.orm.dao.annotation.TableInfo;
import com.season.orm.dao.annotation.Transient;
import com.season.orm.dao.dialect.constant.MySqlTypeConst;
import com.trs.ibook.core.pojo.AbstractPOJO;

import java.util.Date;

import static com.trs.ibook.service.example.OriginPicExample.*;

/**
 * Title:上传原始图实体类
 * Description:上传原始图实体类
 * Copyright: 2019 北京拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company:北京拓尔思信息技术股份有限公司(TRS)
 * Project: ibook
 * Author: KylerTien
 * Create Time:19-3-28 11:33
 */
@TableInfo(tableName = OriginPic.TABLE_NAME, pkName = "id")
public class OriginPic extends AbstractPOJO {

    @Transient
    public static final String TABLE_NAME = "ibook_book_originPic";

    @Column(length = 10, defaultValue = "", autoIncrement = true, comment = N_ID)
    private Integer id;

    @Column(length = 10, notNull = true, comment = N_BOOKID)
    private Integer bookId;

    @Column(length = 255, comment = N_PICURL)
    private String picUrl;

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
