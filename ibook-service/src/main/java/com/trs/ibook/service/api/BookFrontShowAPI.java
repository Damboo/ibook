package com.trs.ibook.service.api;

import com.season.core.Result;
import com.trs.ibook.service.vo.BookPicturePageVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.List;
import java.util.Map;

/**
 * Title:【电子书前端展示相关服务】API
 * Description: swagger接口文档
 * Copyright: 2019 拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company: 拓尔思信息技术股份有限公司(TRS)
 * Project: ibook
 * Author: RayeGong
 * Create Time: 2019-03-13 15:30
 */
@Api(tags = "BookFrontShow", description = "电子书前端展示相关服务")
public interface BookFrontShowAPI {

    /******************************获取电子书目录列表*****************************/
    @ApiOperation(value = "获取电子书目录列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bookId", dataType = "Integer", value = "电子书主键ID", paramType = "query"),
    })
    Result<Map<String, Object>> bookCatalogList(Integer bookId);


    /******************************获取电子相册页展示*****************************/
    @ApiOperation(value = "获取电子相册页展示")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bookId", dataType = "Integer", value = "电子书主键ID", paramType = "query"),
            @ApiImplicitParam(name = "serialNo", dataType = "Integer", value = "序号", paramType = "query"),
    })
    Result<BookPicturePageVO> bookPicturePage(Integer bookId, Integer serialNo);


    /******************************获取电子相册页概览*****************************/
    @ApiOperation(value = "获取电子相册页概览")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bookId", dataType = "Integer", value = "电子书主键ID", paramType = "query"),
            })
    Result<List<BookPicturePageVO>> allBookPicture(Integer bookId);
}
