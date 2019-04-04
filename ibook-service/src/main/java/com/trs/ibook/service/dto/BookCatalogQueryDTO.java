package com.trs.ibook.service.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.trs.ibook.core.constant.JsonFieldConst;
import com.trs.ibook.core.dto.PageQueryDTO;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.validator.constraints.Length;

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

    @ApiModelProperty(notes = N_PAGEINDEX, example = E_PAGEINDEX)
    private String pageIndex;

    @ApiModelProperty(notes = N_CREATETIME, example = E_CREATETIME)
    @JSONField(format = JsonFieldConst.DEFAULT_DATE_FORMAT)
    private String createTime;

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

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
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
