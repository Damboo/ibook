package com.trs.ibook.service.service;

import com.season.common.SafeKit;
import com.season.common.StrKit;
import com.trs.ibook.service.dao.BookInfoDAO;
import com.trs.ibook.service.dao.BookPictureDAO;
import com.trs.ibook.service.pojo.BookInfo;
import com.trs.ibook.service.pojo.BookPicture;
import com.trs.ibook.service.util.ImageUtil;
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

    @RabbitListener(queues = QUEUE)
    public void cutToPage(String[] data) {
        //收到通知,将生产者产出的原始页切分,生成缩略图,存表
        //拼接出目标路径
        try {
            String[] pagePart = ImageUtil.splitImage(data[0], data[1]);
            Integer bookId = SafeKit.getInteger(pagePart[2]);
            String part1 = pagePart[0];
            String part2 = pagePart[1];
            //根据bookid获取book信息
            BookInfo bookInfo = bookInfoDAO.getBookInfoById(bookId);
            //页码存库
            BookPicture bookPicture = new BookPicture();
            bookPicture.setBookId(bookId);
            bookPicture.setCreateTime(new Date());
            bookPicture.setCreateUserId(null);
            bookPicture.setIsDelete(0);
            int serialNo = bookPictureDAO.getNewSerialNoByBookId(bookId);
            int pageIndex = bookPictureDAO.getNewPageIndexByBookId(bookId);
            bookPicture.setSerialNo(serialNo);
            bookPicture.setPageIndex(pageIndex);
            bookPicture.setPicUrl(part1);
            bookPictureDAO.save(bookPicture);
            bookPicture.setSerialNo(serialNo + 1);
            bookPicture.setPageIndex(pageIndex + 1);
            bookPicture.setPicUrl(part2);
            bookPictureDAO.save(bookPicture);
            //如果是第一页上传,并且没有封面,默认设置第一页为封面
            if (serialNo == 1 && StrKit.isEmpty(bookInfo.getCoverUrl())) {
                bookInfo.setCoverUrl(part1);
                bookInfoDAO.updateCoverUrl(bookInfo);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
