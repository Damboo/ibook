package com.trs.ibook.service.util;

import com.season.common.SafeKit;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

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
     * 将一张图切割成两个
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
}
