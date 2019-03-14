package com.trs.ibook.service.service;

import com.season.common.StrKit;
import com.trs.ibook.service.dao.BookCatalogDAO;
import com.trs.ibook.service.dao.BookPictureDAO;
import com.trs.ibook.service.vo.BookCatalogListVO;
import com.trs.ibook.service.vo.BookPicturePageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: 电子书前端展示相关服务
 * Description:
 * Copyright: 2019 拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company: 拓尔思信息技术股份有限公司(TRS)
 * Project: ibook
 * Author: RayeGong
 * Create Time: 2019-03-13 18:35
 */
@Service
public class BookFrontShowService {

    private final BookCatalogDAO bookCatalogDAO;
    private final BookPictureDAO bookPictureDAO;

    @Autowired
    public BookFrontShowService(BookCatalogDAO bookCatalogDAO, BookPictureDAO bookPictureDAO) {
        this.bookCatalogDAO = bookCatalogDAO;
        this.bookPictureDAO = bookPictureDAO;
    }


    /**
     * 获取电子书目录列表服务
     *
     * @param bookId 电子书主键ID
     * @return List<BookCatalogListVO>
     */
    public Map<String, Object> bookCatalogList(Integer bookId) {
        Map<String,Object> bookCatalogMap = new HashMap<>();
        List<BookCatalogListVO> bookCatalogList = bookCatalogDAO.getBookCatalogList(bookId);
        Integer serialTotal = bookPictureDAO.getPictureCountByBookId(bookId);
        bookCatalogMap.put("serialTotal",serialTotal);
        bookCatalogMap.put("bookCatalogList",bookCatalogList);
        return bookCatalogMap;
    }


    /**
     * 获取电子相册页展示服务
     *
     * @param bookId   电子书主键ID
     * @param serialNo 序号
     * @return BookPicturePageVO
     */
    public BookPicturePageVO bookPicturePage(Integer bookId, Integer serialNo) {
        BookPicturePageVO bookPicturePageVO = bookPictureDAO.getBookPicturePage(bookId, serialNo);
        if (bookPicturePageVO == null) {
            return null;
        }
        String picUrl = bookPicturePageVO.getPicUrl();
        if (StrKit.isNotEmpty(picUrl)) {
            String smallPicUrl = picUrl.replace("/normal", "/small");
            bookPicturePageVO.setSmallPicUrl(smallPicUrl);
        }
        return bookPicturePageVO;
    }

}
