package com.trs.ibook.service.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import static com.trs.ibook.service.example.BookPictureExample.*;

/**
 * Title: 电子相册页展示对象
 * Description:
 * Copyright: 2019 拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company: 拓尔思信息技术股份有限公司(TRS)
 * Project: ibook
 * Author: RayeGong
 * Create Time: 2019-03-13 16:00
 */
@ApiModel(description = "电子相册页展示对象")
public class BookPicturePageVO {

    @ApiModelProperty(notes = N_ID, example = E_ID)
    private Integer id;

    @ApiModelProperty(notes = N_BOOKID, example = E_BOOKID)
    private Integer bookId;

    @ApiModelProperty(notes = N_PICURL, example = E_PICURL)
    private String picUrl;

    @ApiModelProperty(notes = "缩略图路径", example = "/hsfile/ibook/small/000001.jpg")
    private String smallPicUrl;

    @ApiModelProperty(notes = N_PAGEINDEX, example = E_PAGEINDEX)
    private Integer pageIndex;

    @ApiModelProperty(notes = N_SERIALNO, example = E_SERIALNO)
    private Integer serialNo;


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

    public String getSmallPicUrl() {
        return smallPicUrl;
    }

    public void setSmallPicUrl(String smallPicUrl) {
        this.smallPicUrl = smallPicUrl;
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

}
