package com.trs.ibook.service.util;

import com.season.common.ArrayKit;
import com.season.common.SafeKit;
import com.season.common.StrKit;
import com.season.common.UUIDUtil;
import com.season.core.error.ParamException;
import com.trs.ibook.core.exception.IBookException;
import com.trs.ibook.core.exception.IBookParamException;
import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;

import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.web.multipart.MultipartFile;

import static com.trs.ibook.service.constant.BookConstant.EXT_NAMES;


/**
 * Title:
 * Description:图片工具类
 * Copyright: 2019 北京拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company:北京拓尔思信息技术股份有限公司(TRS)
 * Project: ibook
 * Author: KylerTien
 * Create Time:19-4-3 11:33
 * @author dambo
 */
public class ImageUtil {
    /**
     * 原始图片上传
     */
    public static String upload(MultipartFile file, String filePath, String dirName) {
        boolean checkResult = checkFile(file, 5 * 1069548);
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
        //重命名文件
        String newFileName = dirName + "_" + System.currentTimeMillis() / 1000 + "_" + UUIDUtil.getUUID() + "." + extName;
        String fileFullName = filePath + newFileName;
        File newFile = new File(fileFullName);
        try {
            file.transferTo(newFile);
        } catch (IOException e) {
            throw new IBookException("上传文件失败");
        }
        return fileFullName;
    }

    /**
     * 检查图片
     */
    private static boolean checkFile(MultipartFile file, int sizeMaxLimit) {
        if (file.getSize() > sizeMaxLimit) {
            return false;
        }
        // 文件扩展名
        String originalFileName = file.getOriginalFilename();
        String extName = originalFileName.substring(originalFileName.indexOf('.') + 1).toLowerCase();
        //获得文件类型（可以判断如果不是图片，禁止上传）
        String contentType = file.getContentType();
        String imageName = contentType.substring(contentType.indexOf('/') + 1);
        if (StrKit.isEmpty(imageName)) {
            return false;
        }
        //判断是不是图片类型
        return ArrayKit.asList(EXT_NAMES).indexOf(extName) != -1;
    }

    /**
     * 将一张图切割成两个,并保存服务器
     *
     * @throws IOException
     */
    public static String[] splitImage(String originImgPath, String targetPath, String albumName) throws IOException {
        String[] str = new String[2];
        // 读入大图
        File file = new File(originImgPath);
        String extName = originImgPath.substring(originImgPath.indexOf('.') + 1).toLowerCase();
        FileInputStream fis = new FileInputStream(file);
        BufferedImage image = ImageIO.read(fis);
        // 分割成1*2(2)个小图
        int rows = 1;
        int cols = 2;
        int chunks = rows * cols;
        // 计算每个小图的宽度和高度
        int chunkWidth = image.getWidth() / cols;
        int chunkHeight = image.getHeight() / rows;
        int count = 0;
        BufferedImage[] imgs = new BufferedImage[chunks];
        for (int x = 0; x < rows; x++) {
            for (int y = 0; y < cols; y++) {
                //设置小图的大小和类型
                imgs[count] = new BufferedImage(chunkWidth, chunkHeight, image.getType());
                //写入图像内容
                Graphics2D gr = imgs[count++].createGraphics();
                gr.drawImage(image, 0, 0,
                        chunkWidth, chunkHeight,
                        chunkWidth * y, chunkHeight * x,
                        chunkWidth * y + chunkWidth,
                        chunkHeight * x + chunkHeight, null);
                gr.dispose();
            }
        }
        // 输出小图
        String part1 = targetPath + albumName + "_" + System.currentTimeMillis() / 1000 + "_" + UUIDUtil.getUUID() + "." + extName;
        String part2 = targetPath + albumName + "_" + System.currentTimeMillis() / 1000 + "." + UUIDUtil.getUUID() + "." + extName;
        ImageIO.write(imgs[0], extName, new File(part1));
        ImageIO.write(imgs[1], extName, new File(part2));
        str[0] = part1;
        str[1] = part2;
        return str;
    }

    /**
     * 生成略缩图,保存服务器
     */
    public static void buildSmallPic(String originImgPath) throws IOException {
        //scale(比例)
        String targetPath = originImgPath.replace("/normal", "/small");
        Thumbnails.of(originImgPath).scale(0.25f).toFile(targetPath);
    }

    /**
     * imageFolderPath:图片所在文件夹
     * pdfPath:指定pdf位置(全路径)
     * 多张图片转pdf
     */
    public static void buildPDF(String imageFolderPath, String pdfPath) {
        try (FileOutputStream fos = new FileOutputStream(pdfPath)) {
            // 图片地址
            String imagePath;
            // 创建文档
            Document doc = new Document(null, 0, 0, 0, 0);
            PdfWriter.getInstance(doc, fos);
            BufferedImage img;
            Image image;
            // 获取图片文件夹对象
            File file = new File(imageFolderPath);
            File[] files = file.listFiles();
            // 循环获取图片文件夹内的图片
            if (files != null) {
                for (File file1 : files) {
                    if (file1.getName().endsWith(".png") || file1.getName().endsWith(".jpg") || file1.getName().endsWith(".jpeg")) {
                        imagePath = imageFolderPath + file1.getName();
                        // 读取图片流
                        img = ImageIO.read(new FileInputStream(new File(imagePath)));
                        doc.setPageSize(new Rectangle(img.getWidth(), img
                                .getHeight()));
                        // 根据图片大小设置文档大小
                        doc.setPageSize(new Rectangle(img.getWidth(), img
                                .getHeight()));
                        // 实例化图片
                        image = Image.getInstance(imagePath);
                        // 添加图片到文档
                        doc.open();
                        doc.add(image);
                    }
                }
            }
            // 关闭文档
            doc.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
