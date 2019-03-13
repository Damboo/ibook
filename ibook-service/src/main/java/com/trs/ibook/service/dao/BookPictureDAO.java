package com.trs.ibook.service.dao;

import com.trs.ibook.core.dao.AbstractDAO;
import com.trs.ibook.service.pojo.BookPicture;
import com.trs.ibook.service.vo.BookPicturePageVO;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Title:【电子书图片表实体类】数据库操作
 * Description:
 * Copyright: 2019 拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company: 拓尔思信息技术股份有限公司(TRS)
 * Project: ibook
 * Author: RayeGong
 * Create Time: 2019-03-13 21:00
 */
@Repository
public class BookPictureDAO extends AbstractDAO<BookPicture> {

    /**
     * 查询电子相册页信息
     *
     * @param bookId   电子书主键ID
     * @param serialNo 序号
     * @return BookPicturePageVO
     */
    public BookPicturePageVO getBookPicturePage(Integer bookId, Integer serialNo) {
        Map<String, Object> params = new HashMap<>();
        params.put("bookId", bookId);
        params.put("serialNo", serialNo);
        String sql = "SELECT id,bookId,picUrl,pageIndex,serialNo FROM ibook_book_picture " +
                "WHERE bookId = :bookId AND serialNo = :serialNo";
        return seasonDao.findFirst(BookPicturePageVO.class, params, sql);
    }

}
