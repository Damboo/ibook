package com.trs.ibook.service.dto;

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
public class BookPictureUpdateDTO {

    @ApiModelProperty(notes = N_ID, example = E_ID)
    @NotNull
    private Integer id;

    @ApiModelProperty(notes = N_BOOKID, example = E_BOOKID)
    @NotNull
    private Integer bookId;

    @ApiModelProperty(notes = N_CATALOGID, example = E_CATALOGID)
    @NotNull
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

    @ApiModelProperty(notes = N_ISDELETE, example = E_ISDELETE)
    @NotNull
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

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }
}
