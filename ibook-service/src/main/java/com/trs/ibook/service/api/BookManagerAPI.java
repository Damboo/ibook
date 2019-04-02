package com.trs.ibook.service.api;

import com.season.core.Result;
import com.trs.ibook.service.pojo.BookInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

/**
 * Title:【电子书后端管理相关服务】API
 * Description:swagger接口文档
 * Copyright: 2019 北京拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company:北京拓尔思信息技术股份有限公司(TRS)
 * Project: ibook
 * Author: KylerTien
 * Create Time:19-3-28 15:36
 *
 * @author dambo
 */
@Api(tags = "BookManager", description = "电子书后端管理相关服务")
public interface BookManagerAPI {

    /******************************WCM新增电子书*****************************/
    @ApiOperation(value = "新增电子书")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "BookInfo", dataType = "BookInfo", value = "提交电子书参数", paramType = "body"),
    })
    Result<Void> addBook(BookInfo bookInfo);


    /******************************WCM编辑电子书*****************************/
    @ApiOperation(value = "编辑电子书")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "Integer", value = "电子书主键ID", paramType = "query"),
    })
    Result<BookInfo> editBook(Integer bookId);


    /******************************WCM修改电子书*****************************/
    @ApiOperation(value = "修改电子书")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "BookInfo", dataType = "BookInfo", value = "修改电子书参数", paramType = "body"),
    })
    Result<Void> updateBook(BookInfo bookInfo);
}
