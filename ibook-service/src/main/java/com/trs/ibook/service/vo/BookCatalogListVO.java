package com.trs.ibook.service.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import static com.trs.ibook.service.example.BookCatalogExample.*;
import static com.trs.ibook.service.example.BookPictureExample.E_SERIALNO;
import static com.trs.ibook.service.example.BookPictureExample.N_SERIALNO;

/**
 * Title: 电子相册目录列表展示对象
 * Description:
 * Copyright: 2019 拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company: 拓尔思信息技术股份有限公司(TRS)
 * Project: ibook
 * Author: RayeGong
 * Create Time: 2019-03-13 16:00
 */
@ApiModel(description = "电子相册目录列表展示对象")
public class BookCatalogListVO {

    @ApiModelProperty(notes = N_ID, example = E_ID)
    private Integer id;

    @ApiModelProperty(notes = N_PARENTID, example = E_PARENTID)
    private Integer parentId;

    @ApiModelProperty(notes = N_BOOKID, example = E_BOOKID)
    private Integer bookId;

    @ApiModelProperty(notes = N_TITLENAME, example = E_TITLENAME)
    private String titleName;

    @ApiModelProperty(notes = N_INTRODUCTION, example = E_INTRODUCTION)
    private String introduction;

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

    public Integer getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(Integer serialNo) {
        this.serialNo = serialNo;
    }

}
