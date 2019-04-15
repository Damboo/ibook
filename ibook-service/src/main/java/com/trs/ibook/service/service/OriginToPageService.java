package com.trs.ibook.service.service;

import com.alibaba.fastjson.JSONObject;
import com.season.common.SafeKit;
import com.season.common.StrKit;
import com.trs.ibook.service.dao.BookInfoDAO;
import com.trs.ibook.service.dao.BookPictureDAO;
import com.trs.ibook.service.pojo.BookInfo;
import com.trs.ibook.service.pojo.BookPicture;
import com.trs.ibook.service.util.ImageUtil;
import org.apache.log4j.Logger;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Date;

import static com.ServiceApp.QUEUE;

/**
 * Title:消费者
 * Description:
 * Copyright: 2019 北京拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company:北京拓尔思信息技术股份有限公司(TRS)
 * Project: ibook
 * Author: KylerTien
 * Create Time:19-4-14 09:37
 */
@Component
public class OriginToPageService {

    @Autowired
    private BookPictureDAO bookPictureDAO;
    @Autowired
    private BookInfoDAO bookInfoDAO;
    private static final Logger logger = Logger.getLogger(OriginToPageService.class);

    @RabbitListener(queues = QUEUE, containerFactory = "rabbitListenerContainerFactory")
    public void cutToPage(String dataMessage, Message message, Channel channel) {
        //收到通知,将生产者产出的原始页切分,生成缩略图,存表
        //拼接出目标路径
        try {
            JSONObject data = JSONObject.parseObject(dataMessage);
            String[] pagePart = ImageUtil.splitImage(SafeKit.getString(data.get("originPath")), SafeKit.getString(data.get("targetPath")));
            Integer bookId = SafeKit.getInteger(SafeKit.getInteger(data.get("bookId")));
            String part1 = pagePart[0];
            String part2 = pagePart[1];
            //根据bookid获取book信息
            BookInfo bookInfo = bookInfoDAO.getBookInfoById(bookId);
            int serialNo = bookPictureDAO.getNewSerialNoByBookId(bookId);
            int pageIndex = bookPictureDAO.getNewPageIndexByBookId(bookId);
            //页码存库
            BookPicture bookPicture1 = new BookPicture();
            bookPicture1.setBookId(bookId);
            bookPicture1.setCreateTime(new Date());
            bookPicture1.setCreateUserId(null);
            bookPicture1.setIsDelete(0);
            bookPicture1.setSerialNo(serialNo);
            bookPicture1.setPageIndex(pageIndex);
            bookPicture1.setPicUrl(part1);
            bookPictureDAO.save(bookPicture1);

            BookPicture bookPicture2 = new BookPicture();
            bookPicture2.setBookId(bookId);
            bookPicture2.setCreateTime(new Date());
            bookPicture2.setCreateUserId(null);
            bookPicture2.setIsDelete(0);
            bookPicture2.setSerialNo(serialNo + 1);
            bookPicture2.setPageIndex(pageIndex + 1);
            bookPicture2.setPicUrl(part2);
            bookPictureDAO.save(bookPicture2);
            //如果是第一页上传,并且没有封面,默认设置第一页为封面
            if (serialNo == 1 && StrKit.isEmpty(bookInfo.getCoverUrl())) {
                bookInfo.setCoverUrl(part1);
                bookInfoDAO.updateCoverUrl(bookInfo);
            }
        } catch (Exception e) {
            logger.error("[print by tk]切页失败!", e);
            try {
                channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
            } catch (IOException e1) {
                logger.error("消费失败!", e);
            }
        }
    }
}
