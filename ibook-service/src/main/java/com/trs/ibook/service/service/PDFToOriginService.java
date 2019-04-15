package com.trs.ibook.service.service;

import com.trs.ibook.service.dao.BookInfoDAO;
import com.trs.ibook.service.dao.OriginPicDAO;
import com.trs.ibook.service.pojo.OriginPic;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.rendering.PDFRenderer;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;

import static com.ServiceApp.QUEUE;

/**
 * Title:生产者
 * Description:
 * Copyright: 2019 北京拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company:北京拓尔思信息技术股份有限公司(TRS)
 * Project: ibook
 * Author: KylerTien
 * Create Time:19-4-14 09:33
 */
@Component
public class PDFToOriginService {

    @Autowired
    private AmqpTemplate amqpTemplate;
    @Autowired
    private BookInfoDAO bookInfoDAO;
    @Autowired
    private OriginPicDAO originPicDAO;
    @Value("${ibook.service.imageUpload.baseDir}")
    private String baseDir;
    @Value("${ibook.service.imageUpload.frontDir}")
    private String frontDir;

    /**
     * 切割PDF为图片
     * 注意:这里使用生产者消费者模式,原图PDF作为生产者切割产出大图,存入origin文件夹,以及写库;
     * 大图存入缓冲区;
     * 切割工具作为消费者,在缓冲区拿出大图并且切割,同时生成略缩图,以及写库;
     * 生产者和消费者均开线程
     */
    public void cutPDF(String pdfUrl, Integer bookId) {
        //首先根据bookId, 获取到文件夹名称
        String albumName = bookInfoDAO.getLocationNameById(bookId);
        OriginPic originPic = new OriginPic();
        originPic.setIsDelete(0);
        originPic.setBookId(bookId);
        originPic.setCreateTime(new Date());
        originPic.setCreateUserId(null);
        // 将pdf转图片 并且自定义图片得格式大小
        File file = new File(frontDir + pdfUrl);
        try {
            PDDocument doc = PDDocument.load(file);
            PDFRenderer renderer = new PDFRenderer(doc);
            int pageCount = doc.getNumberOfPages();
            for (int i = 0; i < pageCount; i++) {
                BufferedImage image = renderer.renderImageWithDPI(i, 144);
                //写出切出的单页图片
                String originPath = baseDir + albumName + "/origin/" + albumName + "_" + (i + 1) + ".png";
                ImageIO.write(image, "png", new File(originPath));
                //存表
                originPic.setSerialNo(i + 1);
                originPic.setPicUrl(originPath);
                originPicDAO.save(originPic);
                //存文件存表后,通知消费者切图,生成缩略图
                String[] data = new String[3];
                data[0] = originPath;
                data[1] = baseDir + albumName + "/normal/" + albumName;
                data[2] = bookId + "";
                this.amqpTemplate.convertAndSend(QUEUE, data);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
