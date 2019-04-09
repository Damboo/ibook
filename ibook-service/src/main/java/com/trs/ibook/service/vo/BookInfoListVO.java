package com.trs.ibook.service.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.trs.ibook.core.constant.JsonFieldConst;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

import static com.trs.ibook.service.example.BookInfoExample.*;
import static com.trs.ibook.service.example.BookInfoExample.E_ISDELETE;
import static com.trs.ibook.service.example.BookInfoExample.N_ISDELETE;

/**
 * Title:
 * Description:电子书详情列表
 * Copyright: 2019 北京拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company:北京拓尔思信息技术股份有限公司(TRS)
 * Project: ibook
 * Author: KylerTien
 * Create Time:19-4-4 14:25
 */
@ApiModel(description = "【电子书信息】列表展示对象")
public class BookInfoListVO {
    @ApiModelProperty(notes = N_ID, example = E_ID)
    private Integer id;

    @ApiModelProperty(notes = N_TITLENAME, example = E_TITLENAME)
    private String titleName;

    @ApiModelProperty(notes = N_INTRODUCTION, example = E_INTRODUCTION)
    private String introduction;

    @ApiModelProperty(notes = N_AUTHOR, example = E_AUTHOR)
    private String author;

    @ApiModelProperty(notes = N_LABELCATEGORY, example = E_LABELCATEGORY)
    private String labelCategory;

    @ApiModelProperty(notes = N_PERIODICAL, example = E_PERIODICAL)
    private Integer periodical;

    @ApiModelProperty(notes = N_PDFURL, example = E_PDFURL)
    private String pdfUrl;

    @ApiModelProperty(notes = N_SITEID, example = E_SITEID)
    private Integer siteId;

    @ApiModelProperty(notes = N_STATUS, example = E_STATUS)
    private Integer status;

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

    public String getPdfUrl() {
        return pdfUrl;
    }

    public void setPdfUrl(String pdfUrl) {
        this.pdfUrl = pdfUrl;
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
