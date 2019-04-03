package com.trs.ibook.service.service;

import com.season.common.ArrayKit;
import com.season.common.JudgeFileTypeKit;
import com.season.common.SafeKit;
import com.season.core.error.ParamException;
import com.trs.ibook.core.exception.IBookException;
import com.trs.ibook.core.exception.IBookParamException;
import com.trs.ibook.service.dao.BookInfoDAO;
import com.trs.ibook.service.dao.BookPictureDAO;
import com.trs.ibook.service.dao.OriginPicDAO;
import com.trs.ibook.service.pojo.BookInfo;
import com.trs.ibook.service.pojo.BookPicture;
import com.trs.ibook.service.pojo.OriginPic;
import com.trs.ibook.service.util.ImageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;

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

    private final OriginPicDAO originPicDAO;
    private final BookInfoDAO bookInfoDAO;
    private final BookPictureDAO bookPictureDAO;
    private static final String[] EXT_NAMES = new String[]{"jpg", "png", "jpeg"};

    @Autowired
    public ImageUploadService(OriginPicDAO originPicDAO, BookInfoDAO bookInfoDAO, BookPictureDAO bookPictureDAO) {
        this.originPicDAO = originPicDAO;
        this.bookInfoDAO = bookInfoDAO;
        this.bookPictureDAO = bookPictureDAO;
    }

    public void imageUpload(MultipartFile multipartFile, String baseDir, Integer bookId) throws IOException {
        //根据bookId获取对应的产品信息
        BookInfo bookInfo = bookInfoDAO.getBookInfoById(bookId);
        String albumName = bookInfo.getTitleName();
        //原始图片的存储路径
        String filePath = baseDir + albumName + File.separator + "origin" + File.separator;
        //切割后的页码存储路径
        String pagePath = baseDir + albumName + File.separator + "normal" + File.separator;
        String fileFullName = upload(multipartFile, filePath, albumName);
        //上传图片后,原图存库
        OriginPic originPic = new OriginPic();
        originPic.setBookId(bookId);
        originPic.setCreateTime(new Date());
        originPic.setCreateUserId(null);
        originPic.setIsDelete(0);
        originPic.setPicUrl(fileFullName);
        //获取当前序列号
        int serialNo = SafeKit.getInteger(fileFullName.substring(fileFullName.indexOf('(') + 1, fileFullName.indexOf(')')));
        originPic.setSerialNo(serialNo);
        originPicDAO.saveOriginPic(originPic);
        //切割原图后, 取到两页路径
        String[] pagePart = ImageUtil.splitImage(fileFullName, pagePath);
        String part1 = pagePart[0];
        int part1Page = SafeKit.getInteger(part1.substring(part1.indexOf('(') + 1, part1.indexOf(')')));
        ImageUtil.buildSmallPic(part1);
        String part2 = pagePart[1];
        int part2Page = SafeKit.getInteger(part2.substring(part2.indexOf('(') + 1, part2.indexOf(')')));
        ImageUtil.buildSmallPic(part2);
        //页码存库
        BookPicture bookPicture = new BookPicture();
        bookPicture.setBookId(bookId);
        bookPicture.setCreateTime(new Date());
        bookPicture.setCreateUserId(null);
        bookPicture.setIsDelete(0);

         bookPicture.setSerialNo(part1Page);
        //固定无页码有5页
        bookPicture.setPageIndex(part1Page > 5 ? part1Page - 5 : null);
        bookPicture.setPicUrl(part1);
        bookPictureDAO.saveBookPicture(bookPicture);

       bookPicture.setSerialNo(part2Page);
        //固定无页码有5页
        bookPicture.setPageIndex(part2Page > 5 ? part2Page - 5 : null);
        bookPicture.setPicUrl(part2);
        bookPictureDAO.saveBookPicture(bookPicture);
    }

    /**
     * 原始图片上传
     */
    private String upload(MultipartFile file, String filePath, String albumName) {
        boolean checkResult = checkFile(file, 5 * 1069548, -1, -1, -1, -1);
        if (!checkResult) {
            throw new IBookException("请上传图片类型为【'jpg', 'jpeg', 'png'】并且小于5MB的图片");
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
        String extName = originalFileName.substring(originalFileName.indexOf('.') + 1).toLowerCase();

        //查库,根据数据库的最新一条页码重命名
        int lastSerialNo = originPicDAO.getLastSerialNo();
        //重命名文件
        String newFileName = albumName + "(" + lastSerialNo + 1 + ")." + extName;
        String fileFullName = filePath + newFileName;
        File newFile = new File(fileFullName);

        try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(newFile))) {
            stream.write(file.getBytes());
        } catch (IOException e) {
            throw new IBookException("upload material error");
        }
        //检查文件类型
        if (ArrayKit.asList(EXT_NAMES).indexOf(extName) == -1 || !JudgeFileTypeKit.checkFileType(newFile, EXT_NAMES)) {
            if (!newFile.delete()) {
                throw new IBookException("delete fail");
            }
            throw new ParamException("file-type-forbid ." + extName);
        }
        return filePath + newFileName;
    }

    private static boolean checkFile(MultipartFile file, int sizeMaxLimit, int heightMaxLimit, int widthMaxLimit, int heightMinLimit, int widthMinLimit) {
        if (file.getSize() > sizeMaxLimit) {
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
}
