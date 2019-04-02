package com.trs.ibook.service.service;

import com.season.common.ArrayKit;
import com.season.common.JudgeFileTypeKit;
import com.season.core.error.ParamException;
import com.trs.ibook.core.exception.IBookException;
import com.trs.ibook.core.exception.IBookParamException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.UUID;

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

    private static boolean check(MultipartFile file, int sizeMaxLimit, int heightMaxLimit, int widthMaxLimit, int heightMinLimit, int widthMinLimit) {
        // 大小限制
        long size = file.getSize();
        if (size > sizeMaxLimit) {
            return false;
        }
        BufferedImage bufferedImg;
        try {
            bufferedImg = ImageIO.read(file.getInputStream());
        } catch (IOException e) {
            throw new IBookException("upload material error");
        }
        if (bufferedImg == null) {
            throw new IBookParamException("所选文件包含不支持的格式或文件大小超出限制，请重新选择");
        }
        int imgWidth = bufferedImg.getWidth();
        int imgHeight = bufferedImg.getHeight();

        if (-1 != heightMinLimit && imgHeight < heightMinLimit) {
            return false;
        }
        if (-1 != widthMinLimit && imgWidth < widthMinLimit) {
            return false;
        }
        if (-1 != heightMaxLimit && imgHeight > heightMaxLimit) {
            return false;
        }
        if (-1 != widthMaxLimit && imgWidth > widthMaxLimit) {
            return false;
        }
        return true;
    }

    public String imageUpload(MultipartFile multipartFile, String baseDir, String albumName) {
        boolean checkResult = check(multipartFile, 5 * 1069548, -1, -1, -1, -1);
        if (!checkResult) {
            throw new IBookException("请上传图片类型为【'jpg', 'jpeg', 'png'】并且小于2MB的图片");
        }
        String filePath = albumName + "origin" + File.separator;
        String fileFullName;
        try {
            fileFullName = upload(multipartFile, baseDir, filePath);
        } catch (Exception e) {
            throw new IBookException("请上传图片类型为【'jpg', 'jpeg', 'png'】并且小于2MB的图片");
        }
        return fileFullName;
    }

    private String upload(MultipartFile file, String baseDir, String filePath) {
        if (file == null || file.isEmpty()) {
            throw new ParamException("null-file");
        }
        //检查目录
        File uploadDir = new File(baseDir + filePath);
        boolean flg = true;
        if (!uploadDir.exists()) {
            flg = uploadDir.mkdirs();
        }
        if (flg && !uploadDir.isDirectory()) {
            throw new ParamException("上传目录不存在" + baseDir + filePath);
        }

        // 文件扩展名
        String originalFileName = file.getOriginalFilename();
        String extName = originalFileName.substring(originalFileName.lastIndexOf(".") + 1, originalFileName
                .length()).toLowerCase();

        //重命名文件
        String newFileName = UUID.randomUUID().toString() + "." + extName;
        String fileFullName = baseDir + filePath + newFileName;
        File newFile = new File(fileFullName);

        try {
            BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(newFile));
            stream.write(file.getBytes());
            stream.close();
        } catch (IOException e) {
            throw new IBookException("upload material error");
        }

        //检查文件类型
        String[] extNames = new String[]{"jpg", "png", "gif", "jpeg"};
        if (ArrayKit.asList(extNames).indexOf(extName) == -1 || !JudgeFileTypeKit.checkFileType(newFile, extNames)) {
            newFile.delete();
            throw new ParamException("file-type-forbid ." + extName);
        }

        return filePath + newFileName;
    }

}
