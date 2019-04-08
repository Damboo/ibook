package com.trs.ibook.service.dto;

import com.trs.ibook.core.dto.AbstractDTO;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.NotNull;

import java.util.Date;

import static com.trs.ibook.service.example.BookCatalogExample.*;


/**
 * Title:
 * Description:
 * Copyright: 2019 北京拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company:北京拓尔思信息技术股份有限公司(TRS)
 * Project: ibook
 * Author: KylerTien
 * Create Time:19-4-4 16:49
 */
public class BookCatalogAddDTO extends AbstractDTO {

    @ApiModelProperty(notes = N_PARENTID, example = E_PARENTID)
    @NotNull
    private Integer parentId;

    @ApiModelProperty(notes = N_BOOKID, example = E_BOOKID)
    @NotNull
    private Integer bookId;

    @ApiModelProperty(notes = N_TITLENAME, example = E_TITLENAME)
    @NotBlank(message = "目录标题不能为空")
    @Length(max = 30, message = "titleName最大长度不能超过{max}")
    private String titleName;

    @ApiModelProperty(notes = N_INTRODUCTION, example = E_INTRODUCTION)
    @NotBlank(message = "目录简介不能为空")
    @Length(max = 100, message = "introduction最大长度不能超过{max}")
    private String introduction;

    @ApiModelProperty(notes = N_PAGEINDEX, example = E_PAGEINDEX)
    @NotNull
    private String pageIndex;

    @ApiModelProperty(notes = N_CREATETIME, example = E_CREATETIME)

    private Date createTime;

    @ApiModelProperty(notes = N_CREATEUSERID, example = E_CREATEUSERID)
    @NotNull
    private String createUserId;

    @ApiModelProperty(notes = N_ISDELETE, example = E_ISDELETE)
    @NotNull
    private String isDelete;

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

    public String getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(String pageIndex) {
        this.pageIndex = pageIndex;
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
