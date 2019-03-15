package com.trs.ibook.service.dao;

import com.trs.ibook.core.dao.AbstractDAO;
import com.trs.ibook.service.pojo.BookCatalog;
import com.trs.ibook.service.pojo.BookPicture;
import com.trs.ibook.service.vo.BookCatalogListVO;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title:【电子书目录表实体类】数据库操作
 * Description:
 * Copyright: 2019 拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company: 拓尔思信息技术股份有限公司(TRS)
 * Project: ibook
 * Author: RayeGong
 * Create Time: 2019-03-13 21:00
 */
@Repository
public class BookCatalogDAO extends AbstractDAO<BookCatalog> {

    /**
     * 查询电子书目录列表
     *
     * @param bookId 电子书主键ID
     * @return List<BookCatalogListVO>
     */
    public List<BookCatalogListVO> getBookCatalogList(Integer bookId) {
        Map<String, Object> params = new HashMap<>();
        params.put("bookId", bookId);
        String sql = "SELECT c.id,c.parentId,c.bookId,c.titleName,c.introduction,c.pageIndex,p.serialNo " +
                "FROM " + BookCatalog.TABLE_NAME + " AS c " +
                "LEFT JOIN " + BookPicture.TABLE_NAME + " AS p ON c.pageIndex = p.pageIndex " +
                "WHERE c.bookId = :bookId AND p.bookId = :bookId AND c.isDelete = 0 AND p.isDelete = 0 " +
                "ORDER BY c.pageIndex ASC ";
        return seasonDao.find(BookCatalogListVO.class, params, sql);
    }

}
