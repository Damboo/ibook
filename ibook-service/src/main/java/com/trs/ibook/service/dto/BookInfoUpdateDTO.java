package com.trs.ibook.service.dto;

import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

import static com.trs.ibook.service.example.BookInfoExample.*;

/**
 * Title:
 * Description:
 * Copyright: 2019 北京拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company:北京拓尔思信息技术股份有限公司(TRS)
 * Project: ibook
 * Author: KylerTien
 * Create Time:19-3-28 17:37
 */
public class BookInfoUpdateDTO {

    @ApiModelProperty(notes = N_ID, example = E_ID)
    @NotNull
    private Integer id;

    @ApiModelProperty(notes = N_TITLENAME, example = E_TITLENAME)
    @NotBlank(message = "电子书标题不能为空")
    @Length(max = 30, message = "titleName最大长度不能超过{max}")
    private String titleName;

    @ApiModelProperty(notes = N_INTRODUCTION, example = E_INTRODUCTION)
    @NotBlank(message = "简介不能为空")
    @Length(max = 100, message = "introduction最大长度不能超过{max}")
    private String introduction;

    @ApiModelProperty(notes = N_AUTHOR, example = E_AUTHOR)
    @NotBlank(message = "作者不能为空")
    @Length(max = 10, message = "author最大长度不能超过{max}")
    private String author;

    @ApiModelProperty(notes = N_LABELCATEGORY, example = E_LABELCATEGORY)
    @NotNull
    private String labelCategory;

    @ApiModelProperty(notes = N_PERIODICAL, example = E_PERIODICAL)
    @NotNull
    private Integer periodical;

    @ApiModelProperty(notes = N_PDFURL, example = E_PDFURL)
    @NotNull
    private String pdfUrl;

    @ApiModelProperty(notes = N_SITEID, example = E_SITEID)
    @NotNull
    private String siteId;

    @ApiModelProperty(notes = N_STATUS, example = E_STATUS)
    @NotNull
    private String status;

    @ApiModelProperty(notes = N_ISDELETE, example = E_ISDELETE)
    @NotNull
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

    public String getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(String isDelete) {
        this.isDelete = isDelete;
    }
}
