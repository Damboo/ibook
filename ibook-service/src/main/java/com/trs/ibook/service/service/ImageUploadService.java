package com.trs.ibook.service.service;

import com.season.common.ArrayKit;
import com.season.common.JudgeFileTypeKit;
import com.season.core.error.ParamException;
import com.trs.ibook.core.exception.IBookException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Title: 图片上传服务
 * Description:
 * Copyright: 2017 北京拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company: 北京拓尔思信息技术股份有限公司(TRS)
 * Project: trs-interaction
 * Author: RayeGong
 * Create Time: 2018-01-29 10:30
 */
@Service
public class ImageUploadService {
    public String imageUpload(MultipartFile multipartFile, String baseDir, String albumName) {
        String filePath = baseDir + albumName + File.separator + "origin" + File.separator;
        String fileFullName;
        try {
            fileFullName = upload(multipartFile, filePath);
        } catch (Exception e) {
            throw new IBookException("请上传图片类型为【'jpg', 'jpeg', 'png'】并且小于2MB的图片");
        }
        return fileFullName;
    }

    private String upload(MultipartFile file, String filePath) {
        if (file == null || file.isEmpty()) {
            throw new ParamException("null-file");
        }
        //检查目录
        File uploadDir = new File(filePath);
        boolean flg = true;
        if (!uploadDir.exists()) {
            flg = uploadDir.mkdirs();
        }
        if (flg && !uploadDir.isDirectory()) {
            throw new ParamException("上传目录不存在" + filePath);
        }

        // 文件扩展名
        String originalFileName = file.getOriginalFilename();
        String extName = originalFileName.substring(originalFileName.indexOf(".") + 1).toLowerCase();

        //重命名文件
        String newFileName = "." + extName;
        String fileFullName = filePath + newFileName;
        File newFile = new File(fileFullName);

        try {
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(newFile));
            stream.write(file.getBytes());
            stream.close();
        } catch (IOException e) {
            throw new IBookException("upload material error");
        }

        //检查文件类型
        String[] extNames = new String[]{"jpg", "png", "jpeg"};
        if (ArrayKit.asList(extNames).indexOf(extName) == -1 || !JudgeFileTypeKit.checkFileType(newFile, extNames)) {
            newFile.delete();
            throw new ParamException("file-type-forbid ." + extName);
        }

        return filePath + newFileName;
    }
}
