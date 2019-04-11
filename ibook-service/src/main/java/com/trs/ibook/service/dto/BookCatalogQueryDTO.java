package com.trs.ibook.service.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.trs.ibook.core.constant.JsonFieldConst;
import com.trs.ibook.core.dto.PageQueryDTO;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

import java.util.Date;

import static com.trs.ibook.service.example.BookCatalogExample.*;

/**
 * Title:
 * Description:
 * Copyright: 2019 北京拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company:北京拓尔思信息技术股份有限公司(TRS)
 * Project: ibook
 * Author: KylerTien
 * Create Time:19-4-4 16:50
 */
public class BookCatalogQueryDTO extends PageQueryDTO {

    @ApiModelProperty(notes = N_ID, example = E_ID)
    private Integer id;

    @ApiModelProperty(notes = N_PARENTID, example = E_PARENTID)
    private Integer parentId;

    @ApiModelProperty(notes = N_BOOKID, example = E_BOOKID)
    private Integer bookId;

    @ApiModelProperty(notes = N_TITLENAME, example = E_TITLENAME)
    @Length(max = 30, message = "titleName最大长度不能超过{max}")
    private String titleName;

    @ApiModelProperty(notes = N_INTRODUCTION, example = E_INTRODUCTION)
    @Length(max = 60, message = "introduction最大长度不能超过{max}")
    private String introduction;

    @ApiModelProperty(notes = N_PAGESTARTINDEX, example = E_PAGESTARTINDEX)
    private Integer pageStartIndex;

    @ApiModelProperty(notes = N_PAGEENDINDEX, example = E_PAGEENDINDEX)
    private Integer pageEndIndex;

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

    public Integer getPageStartIndex() {
        return pageStartIndex;
    }

    public void setPageStartIndex(Integer pageStartIndex) {
        this.pageStartIndex = pageStartIndex;
    }

    public Integer getPageEndIndex() {
        return pageEndIndex;
    }

    public void setPageEndIndex(Integer pageEndIndex) {
        this.pageEndIndex = pageEndIndex;
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
