package com.trs.ibook.service.api;

import com.season.core.Result;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.multipart.MultipartFile;

/**
 * Title:
 * Description:电子书图片上传
 * Copyright: 2019 北京拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company:北京拓尔思信息技术股份有限公司(TRS)
 * Project: ibook
 * Author: KylerTien
 * Create Time:19-3-29 10:51
 */
@Api(tags = "ImageUpload", description = "电子书图片上传")
public interface ImageUploadAPI {

    /******************************电子书图片上传*****************************/
    @ApiOperation(value = "电子书图片上传")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "multipartFile", dataType = "MultipartFile", value = "文件内容", paramType = "body", allowMultiple = true),
            @ApiImplicitParam(name = "albumName", dataType = "String", value = "电子书名称", paramType = "body"),
    })
    Result<String> imageUpload(MultipartFile multipartFile, String albumName);
}
