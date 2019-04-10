package com.trs.ibook.service.api;

import com.season.core.Page;
import com.season.core.Result;
import com.trs.ibook.service.dto.BookInfoAddDTO;
import com.trs.ibook.service.dto.BookInfoQueryDTO;
import com.trs.ibook.service.dto.BookInfoUpdateDTO;
import com.trs.ibook.service.vo.BookInfoListVO;
import com.trs.ibook.service.vo.BookInfoShowVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;

import java.util.List;

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
@Api(tags = "BookInfo", description = "电子书后端管理相关服务")
public interface BookInfoAPI {

    /******************************新增【电子书信息】*****************************/
    @ApiOperation(value = "新增【电子书信息】")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bookInfoAddDTO", dataType = "BookInfoAddDTO", value = "新增【电子书信息】参数", paramType = "body"),
    })
    Result<Integer> save(BookInfoAddDTO bookInfoAddDTO);


    /******************************分页查询【电子书信息】*****************************/
    @ApiOperation(value = "分页查询【电子书信息】")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bookInfoQueryDTO", dataType = "BookInfoQueryDTO", value = "分页查询【电子书信息】参数", paramType = "body"),
    })
    Result<Page<BookInfoListVO>> page(BookInfoQueryDTO bookInfoQueryDTO);


    /******************************WCM修改电子书*****************************/
    @ApiOperation(value = "修改电子书")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bookInfoUpdateDTO", dataType = "BookInfoUpdateDTO", value = "修改电子书参数", paramType = "body"),
    })
    Result<Void> update(BookInfoUpdateDTO bookInfoUpdateDTO);


    /******************************查询【电子书信息】集合*****************************/
    @ApiOperation(value = "查询【电子书信息】集合")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bookInfoQueryDTO", dataType = "BookInfoQueryDTO", value = "分页查询【电子书信息】参数", paramType = "body"),
    })
    Result<List<BookInfoListVO>> list(BookInfoQueryDTO bookInfoQueryDTO);


    /******************************查看【电子书信息】详情*****************************/
    @ApiOperation(value = "查看【电子书信息】详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "Integer", value = "【电子书信息】id", paramType = "query"),
    })
    Result<BookInfoShowVO> show(Integer id);


    /******************************删除【电子书信息】*****************************/
    @ApiOperation(value = "删除【电子书信息】")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "Integer", value = "【电子书信息】id", paramType = "path"),
    })
    Result<Integer> delete(Integer id);

    /******************************导出电子书PDF*****************************/
    @ApiOperation(value = "导出电子书PDF")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "Integer", value = "【电子书信息】id", paramType = "path"),
    })
    Result<Integer> loadPDF(Integer id);

}
