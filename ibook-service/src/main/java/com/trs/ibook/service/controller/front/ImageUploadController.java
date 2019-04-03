package com.trs.ibook.service.controller.front;

import com.season.common.StrKit;
import com.season.core.Result;
import com.trs.ibook.core.exception.IBookException;
import com.trs.ibook.core.exception.IBookParamException;
import com.trs.ibook.service.api.ImageUploadAPI;
import com.trs.ibook.service.service.ImageUploadService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


/**
 * Title:
 * Description:
 * Copyright: 2019 北京拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company:北京拓尔思信息技术股份有限公司(TRS)
 * Project: ibook
 * Author: KylerTien
 * Create Time:19-3-29 10:55
 */
@RestController
@RequestMapping("/")
public class ImageUploadController implements ImageUploadAPI {

    @Autowired
    private ImageUploadService imageUploadService;
    @Value("${ibook.service.imageUpload.baseDir}")
    private String baseDir;

    @Override
    @RequestMapping(method = RequestMethod.POST, value = "imageUpload")
    public Result imageUpload(@RequestPart MultipartFile multipartFile, Integer bookId) {
        Result result = Result.success();
        if (multipartFile == null) {
            throw new IBookException("图片不能为空");
        }
        if (StrKit.isEmpty(bookId)) {
            throw new IBookParamException("电子书id不能为空");
        }
        try {
            imageUploadService.imageUpload(multipartFile, baseDir, bookId);
        } catch (IOException e) {
            result.setIsSuccess(false);
            return result;
        }
        return result;
    }
}
