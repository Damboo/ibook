package com.trs.ibook.service.dto;

import com.season.orm.dao.annotation.Column;
import com.season.orm.dao.dialect.constant.MySqlTypeConst;
import com.trs.ibook.core.dto.AbstractDTO;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.util.Date;

import static com.trs.ibook.service.example.BookPictureExample.*;

/**
 * Title:
 * Description:
 * Copyright: 2019 北京拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company:北京拓尔思信息技术股份有限公司(TRS)
 * Project: ibook
 * Author: KylerTien
 * Create Time:19-4-9 14:04
 */
public class BookPictureAddDTO extends AbstractDTO {

    @ApiModelProperty(notes = N_BOOKID, example = E_BOOKID)
    @NotNull
    private Integer bookId;

    @ApiModelProperty(notes = N_CATALOGID, example = E_CATALOGID)
    private Integer catalogId;

    @ApiModelProperty(notes = N_PICURL, example = E_PICURL)
    @NotNull
    private String picUrl;

    @ApiModelProperty(notes = N_PAGEINDEX, example = E_PAGEINDEX)
    @NotNull
    private Integer pageIndex;

    @ApiModelProperty(notes = N_SERIALNO, example = E_SERIALNO)
    @NotNull
    private Integer serialNo;

    @ApiModelProperty(notes = N_CREATETIME, example = E_CREATETIME)
    private Date createTime;

    @ApiModelProperty(notes = N_CREATEUSERID, example = E_CREATEUSERID)
    private Long createUserId;

    @ApiModelProperty(notes = N_ISDELETE, example = E_ISDELETE)
    private Integer isDelete;

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
