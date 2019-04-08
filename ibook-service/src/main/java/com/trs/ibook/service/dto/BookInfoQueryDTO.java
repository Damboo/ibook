package com.trs.ibook.service.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.trs.ibook.core.constant.JsonFieldConst;
import com.trs.ibook.core.dto.PageQueryDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

import static com.trs.ibook.service.example.BookInfoExample.*;
import static com.trs.ibook.service.example.BookInfoExample.E_ISDELETE;
import static com.trs.ibook.service.example.BookInfoExample.N_ISDELETE;

/**
 * Title:查询【电子书信息】的参数
 * Description:查询【电子书信息】的参数
 * Copyright: 2019 北京拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company:北京拓尔思信息技术股份有限公司(TRS)
 * Project: ibook
 * Author: KylerTien
 * Create Time:19-4-4 14:27
 */
@ApiModel(description = "查询【电子书信息】的参数")
public class BookInfoQueryDTO extends PageQueryDTO {
    @ApiModelProperty(notes = N_ID, example = E_ID)
    private Integer id;

    @ApiModelProperty(notes = N_TITLENAME, example = E_TITLENAME)
    @Length(max = 30, message = "titleName最大长度不能超过{max}")
    private String titleName;

    @ApiModelProperty(notes = N_INTRODUCTION, example = E_INTRODUCTION)
    @Length(max = 100, message = "introduction最大长度不能超过{max}")
    private String introduction;

    @ApiModelProperty(notes = N_AUTHOR, example = E_AUTHOR)
    @Length(max = 10, message = "author最大长度不能超过{max}")
    private String author;

    @ApiModelProperty(notes = N_LABELCATEGORY, example = E_LABELCATEGORY)
    private String labelCategory;

    @ApiModelProperty(notes = N_PERIODICAL, example = E_PERIODICAL)
    private Integer periodical;

    @ApiModelProperty(notes = N_PDFURL, example = E_PDFURL)
    private String pdfUrl;

    @ApiModelProperty(notes = N_SITEID, example = E_SITEID)
    private String siteId;

    @ApiModelProperty(notes = N_STATUS, example = E_STATUS)
    private String status;

    @ApiModelProperty(notes = N_CREATETIME, example = E_CREATETIME)
    @JSONField(format = JsonFieldConst.DEFAULT_DATETIME_FORMAT)
    private Date createTime;

    @ApiModelProperty(notes = N_CREATEUSERID, example = E_CREATEUSERID)
    private String createUserId;

    @ApiModelProperty(notes = N_ISDELETE, example = E_ISDELETE)
    private String isDelete;

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

    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(String createUserId) {
        this.createUserId = createUserId;
    }

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }
}
