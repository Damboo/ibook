package com.trs.ibook.service.api;

import com.season.core.Page;
import com.season.core.Result;
import com.trs.ibook.service.dto.BookCatalogAddDTO;
import com.trs.ibook.service.dto.BookCatalogQueryDTO;
import com.trs.ibook.service.dto.BookCatalogUpdateDTO;
import com.trs.ibook.service.vo.BookCatalogListVO;
import com.trs.ibook.service.vo.BookCatalogShowVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.List;

/**
 * Title:
 * Description:
 * Copyright: 2019 北京拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company:北京拓尔思信息技术股份有限公司(TRS)
 * Project: ibook
 * Author: KylerTien
 * Create Time:19-4-4 16:30
 */
@Api(tags = "BookCatalog", description = "目录管理")
public interface BookCatalogAPI {

    /******************************新增【目录信息】*****************************/
    @ApiOperation(value = "新增【目录信息】")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bookCatalogAddDTO", dataType = "BookCatalogAddDTO", value = "新增【目录信息】参数", paramType = "body"),
    })
    Result<Integer> save(BookCatalogAddDTO bookCatalogAddDTO);


    /******************************分页查询【目录信息】*****************************/
    @ApiOperation(value = "分页查询【目录信息】")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bookCatalogQueryDTO", dataType = "BookCatalogQueryDTO", value = "分页查询【目录信息】参数", paramType = "body"),
    })
    Result<Page<BookCatalogListVO>> page(BookCatalogQueryDTO bookCatalogQueryDTO);


    /******************************WCM修改目录*****************************/
    @ApiOperation(value = "修改目录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bookCatalogUpdateDTO", dataType = "BookCatalogUpdateDTO", value = "修改目录参数", paramType = "body"),
    })
    Result<Void> update(BookCatalogUpdateDTO bookCatalogUpdateDTO);


    /******************************查询【目录信息】集合*****************************/
    @ApiOperation(value = "查询【目录信息】集合")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bookCatalogQueryDTO", dataType = "BookCatalogQueryDTO", value = "分页查询【目录信息】参数", paramType = "body"),
    })
    Result<List<BookCatalogListVO>> list(BookCatalogQueryDTO bookCatalogQueryDTO);


    /******************************查看【目录信息】详情*****************************/
    @ApiOperation(value = "查看【目录信息】详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "Integer", value = "【目录信息】id", paramType = "query"),
    })
    Result<BookCatalogShowVO> show(Integer id);


    /******************************删除【目录信息】*****************************/
    @ApiOperation(value = "删除【目录信息】")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "Integer", value = "【目录信息】id", paramType = "path"),
    })
    Result<Integer> delete(Integer id);

    /******************************排序【电子书目录信息】*****************************/
    @ApiOperation(value = "排序【电子书目录信息】")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "Integer", value = "【电子书目录信息】id", paramType = "path"),
            @ApiImplicitParam(name = "type", dataType = "Integer", value = "排序顺序属性", paramType = "path"),
    })
    Result<Void> sort(Integer id,Integer type);
}
