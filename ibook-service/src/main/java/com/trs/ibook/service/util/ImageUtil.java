package com.trs.ibook.service.util;

import com.season.common.SafeKit;
import net.coobird.thumbnailator.Thumbnails;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfWriter;


/**
 * Title:
 * Description:图片工具类
 * Copyright: 2019 北京拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company:北京拓尔思信息技术股份有限公司(TRS)
 * Project: ibook
 * Author: KylerTien
 * Create Time:19-4-3 11:33
 */
public class ImageUtil {

    /**
     * 将一张图切割成两个,并保存服务器
     *
     * @throws IOException
     */
    public static String[] splitImage(String originImgPath, String targetPath) throws IOException {
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
        //获取当前序列号
        int serialNo = SafeKit.getInteger(originImgPath.substring(originImgPath.indexOf('(') + 1, originImgPath.indexOf(')')));
        //获取当前文件名
        String fileName = originImgPath.substring(originImgPath.indexOf('/') + 1, originImgPath.indexOf('('));
        // 输出小图
        String part1 = targetPath + fileName + "(" + (serialNo * 2 - 1) + ")" + "." + extName;
        String part2 = targetPath + fileName + "(" + (serialNo * 2) + ")" + "." + extName;
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
     * pdfPath:指定pdf位置
     * 多张图片转pdf
     */
    public static void buildPDF(String imageFolderPath, String pdfPath) {
        try (FileOutputStream fos = new FileOutputStream(pdfPath)){
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
