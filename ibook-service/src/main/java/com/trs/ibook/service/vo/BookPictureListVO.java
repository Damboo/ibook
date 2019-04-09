package com.trs.ibook.service.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.trs.ibook.core.constant.JsonFieldConst;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

import static com.trs.ibook.service.example.BookPictureExample.*;
import static com.trs.ibook.service.example.BookPictureExample.E_ISDELETE;
import static com.trs.ibook.service.example.BookPictureExample.N_ISDELETE;

/**
 * Title:
 * Description:
 * Copyright: 2019 北京拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company:北京拓尔思信息技术股份有限公司(TRS)
 * Project: ibook
 * Author: KylerTien
 * Create Time:19-4-9 14:25
 */
@ApiModel(description = "电子书页码列表展示对象")
public class BookPictureListVO {

    @ApiModelProperty(notes = N_ID, example = E_ID)
    private Integer id;

    @ApiModelProperty(notes = N_BOOKID, example = E_BOOKID)
    private Integer bookId;

    @ApiModelProperty(notes = N_CATALOGID, example = E_CATALOGID)
    private Integer catalogId;

    @ApiModelProperty(notes = N_PICURL, example = E_PICURL)
    private String picUrl;

    @ApiModelProperty(notes = N_PAGEINDEX, example = E_PAGEINDEX)
    private Integer pageIndex;

    @ApiModelProperty(notes = N_SERIALNO, example = E_SERIALNO)
    private Integer serialNo;

    @ApiModelProperty(notes = N_CREATETIME, example = E_CREATETIME)
    @JSONField(format = JsonFieldConst.DEFAULT_DATETIME_FORMAT)
    private Date createTime;

    @ApiModelProperty(notes = N_CREATEUSERID, example = E_CREATEUSERID)
    private Long createUserId;

    @ApiModelProperty(notes = N_ISDELETE, example = E_ISDELETE)
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

    public Integer getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(Integer catalogId) {
        this.catalogId = catalogId;
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
