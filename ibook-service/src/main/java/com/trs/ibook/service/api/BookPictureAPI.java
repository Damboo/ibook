package com.trs.ibook.service.api;

import com.season.core.Page;
import com.season.core.Result;
import com.trs.ibook.service.dto.BookPictureQueryDTO;
import com.trs.ibook.service.dto.BookPictureUpdateDTO;
import com.trs.ibook.service.vo.BookPictureListVO;
import com.trs.ibook.service.vo.BookPictureShowVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Title:
 * Description:电子书图片上传
 * Copyright: 2019 北京拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company:北京拓尔思信息技术股份有限公司(TRS)
 * Project: ibook
 * Author: KylerTien
 * Create Time:19-3-29 10:51
 */
@Api(tags = "BookPicture", description = "电子书页码管理相关服务")
public interface BookPictureAPI {

    /******************************分页查询【电子书页码信息】*****************************/
    @ApiOperation(value = "分页查询【电子书页码信息】")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bookPictureQueryDTO", dataType = "BookPictureQueryDTO", value = "分页查询【电子书页码信息】参数", paramType = "body"),
    })
    Result<Page<BookPictureListVO>> page(BookPictureQueryDTO bookPictureQueryDTO);


    /******************************WCM修改电子书页码*****************************/
    @ApiOperation(value = "修改电子书页码")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bookPictureUpdateDTO", dataType = "BookPictureUpdateDTO", value = "修改电子书页码参数", paramType = "body"),
    })
    Result<Void> update(BookPictureUpdateDTO bookPictureUpdateDTO);


    /******************************查询【电子书页码信息】集合*****************************/
    @ApiOperation(value = "查询【电子书页码信息】集合")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "bookPictureQueryDTO", dataType = "BookPictureQueryDTO", value = "分页查询【电子书页码信息】参数", paramType = "body"),
    })
    Result<List<BookPictureListVO>> list(BookPictureQueryDTO bookPictureQueryDTO);


    /******************************查看【电子书页码信息】详情*****************************/
    @ApiOperation(value = "查看【电子书页码信息】详情")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "Integer", value = "【电子书页码信息】id", paramType = "query"),
    })
    Result<BookPictureShowVO> show(Integer id);


    /******************************删除【电子书页码信息】*****************************/
    @ApiOperation(value = "删除【电子书页码信息】")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id", dataType = "Integer", value = "【电子书页码信息】id", paramType = "path"),
    })
    Result<Integer> delete(Integer id);


    /******************************电子书图片上传*****************************/
    @ApiOperation(value = "电子书图片上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "multipartFile", dataType = "MultipartFile", value = "文件内容", paramType = "body", allowMultiple = true),
            @ApiImplicitParam(name = "bookId", dataType = "Integer", value = "电子书id", paramType = "body"),
    })
    Result<String> imageUpload(MultipartFile multipartFile, Integer bookId);
}
