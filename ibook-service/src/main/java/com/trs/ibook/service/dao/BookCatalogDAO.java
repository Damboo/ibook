package com.trs.ibook.service.dao;

import com.season.common.StrKit;
import com.season.core.Page;
import com.trs.ibook.core.dao.AbstractDAO;
import com.trs.ibook.service.dto.BookCatalogQueryDTO;
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

    /**
     * 分页查询电子书
     */
    public Page<BookCatalogListVO> findByQuery(BookCatalogQueryDTO bookCatalogQueryDTO) {
        Map<String, Object> params = new HashMap<>();
        String sql = "select * from " + BookCatalog.TABLE_NAME + " t where isDelete = 0 ";
        if (StrKit.isNotEmpty(bookCatalogQueryDTO.getId())) {
            sql += "and t.id = :id ";
            params.put("id", bookCatalogQueryDTO.getId());
        }
        if (StrKit.isNotEmpty(bookCatalogQueryDTO.getBookId())) {
            sql += "and t.bookId = :bookId ";
            params.put("bookId", bookCatalogQueryDTO.getBookId());
        }
        if (StrKit.isNotEmpty(bookCatalogQueryDTO.getIntroduction())) {
            sql += "and t.introduction = :introduction ";
            params.put("introduction", bookCatalogQueryDTO.getIntroduction());
        }
        if (StrKit.isNotEmpty(bookCatalogQueryDTO.getPageIndex())) {
            sql += "and t.pageIndex = :pageIndex ";
            params.put("pageIndex", bookCatalogQueryDTO.getPageIndex());
        }
        if (StrKit.isNotEmpty(bookCatalogQueryDTO.getTitleName())) {
            sql += "and t.titleName = :titleName ";
            params.put("titleName", bookCatalogQueryDTO.getTitleName());
        }
        return seasonDao.findPage(BookCatalogListVO.class, bookCatalogQueryDTO.getPageNo(),
                bookCatalogQueryDTO.getPageSize(), params, sql);
    }

    /**
     * 分页查询电子书
     */
    public List<BookCatalogListVO> queryList(BookCatalogQueryDTO bookCatalogQueryDTO) {
        Map<String, Object> params = new HashMap<>();
        String sql = "select * from " + BookCatalog.TABLE_NAME + " t where isDelete = 0 ";
        if (StrKit.isNotEmpty(bookCatalogQueryDTO.getId())) {
            sql += "and t.id = :id ";
            params.put("id", bookCatalogQueryDTO.getId());
        }
        if (StrKit.isNotEmpty(bookCatalogQueryDTO.getBookId())) {
            sql += "and t.bookId = :bookId ";
            params.put("bookId", bookCatalogQueryDTO.getBookId());
        }
        if (StrKit.isNotEmpty(bookCatalogQueryDTO.getIntroduction())) {
            sql += "and t.introduction = :introduction ";
            params.put("introduction", bookCatalogQueryDTO.getIntroduction());
        }
        if (StrKit.isNotEmpty(bookCatalogQueryDTO.getPageIndex())) {
            sql += "and t.pageIndex = :pageIndex ";
            params.put("pageIndex", bookCatalogQueryDTO.getPageIndex());
        }
        if (StrKit.isNotEmpty(bookCatalogQueryDTO.getTitleName())) {
            sql += "and t.titleName = :titleName ";
            params.put("titleName", bookCatalogQueryDTO.getTitleName());
        }
        return seasonDao.find(BookCatalogListVO.class, sql, params);
    }

}
