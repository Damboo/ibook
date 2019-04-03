package com.trs.ibook.service.dao;

import com.trs.ibook.core.dao.AbstractDAO;
import com.trs.ibook.service.pojo.BookInfo;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Title:【电子书信息表实体类】数据库操作
 * Description:
 * Copyright: 2019 拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company: 拓尔思信息技术股份有限公司(TRS)
 * Project: ibook
 * Author: RayeGong
 * Create Time: 2019-03-13 21:00
 */
@Repository
public class BookInfoDAO extends AbstractDAO<BookInfo> {

    public BookInfo getBookInfoById(Integer id) {
        String sql = "SELECT * FROM " + BookInfo.TABLE_NAME + " WHERE id =? AND isDelete = 0 ";
        return seasonDao.findFirst(BookInfo.class, sql, id);
    }

}
