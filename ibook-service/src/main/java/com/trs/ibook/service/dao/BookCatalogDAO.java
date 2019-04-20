package com.trs.ibook.service.dao;

import com.season.common.SafeKit;
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
        String sql = " SELECT c.id,c.parentId,c.bookId,c.titleName,c.introduction,c.pageStartIndex,p.serialNo " +
                " FROM " + BookCatalog.TABLE_NAME + " AS c " +
                " LEFT JOIN " + BookPicture.TABLE_NAME + " AS p ON c.pageStartIndex = p.pageIndex " +
                " WHERE c.bookId = :bookId AND p.bookId = :bookId AND c.isDelete = 0 AND p.isDelete = 0 " +
                " ORDER BY c.pageStartIndex ASC ";
        return seasonDao.find(BookCatalogListVO.class, params, sql);
    }

    /**
     * 分页查询电子书目录
     */
    public Page<BookCatalogListVO> findByQuery(BookCatalogQueryDTO bookCatalogQueryDTO) {
        Map<String, Object> params = new HashMap<>();
        String sql = " select * from " + BookCatalog.TABLE_NAME + " t where isDelete = 0 ";
        if (StrKit.isNotEmpty(bookCatalogQueryDTO.getId())) {
            sql += " and t.id = :id ";
            params.put("id", bookCatalogQueryDTO.getId());
        }
        if (StrKit.isNotEmpty(bookCatalogQueryDTO.getBookId())) {
            sql += " and t.bookId = :bookId ";
            params.put("bookId", bookCatalogQueryDTO.getBookId());
        }
        if (StrKit.isNotEmpty(bookCatalogQueryDTO.getIntroduction())) {
            sql += " and t.introduction = :introduction ";
            params.put("introduction", bookCatalogQueryDTO.getIntroduction());
        }
        if (StrKit.isNotEmpty(bookCatalogQueryDTO.getPageStartIndex())) {
            sql += " and t.pageStartIndex = :pageStartIndex ";
            params.put("pageStartIndex", bookCatalogQueryDTO.getPageStartIndex());
        }
        if (StrKit.isNotEmpty(bookCatalogQueryDTO.getPageEndIndex())) {
            sql += " and t.pageEndIndex = :pageEndIndex ";
            params.put("pageEndIndex", bookCatalogQueryDTO.getPageEndIndex());
        }
        if (StrKit.isNotEmpty(bookCatalogQueryDTO.getTitleName())) {
            sql += " and t.titleName = :titleName ";
            params.put("titleName", bookCatalogQueryDTO.getTitleName());
        }
        //默认按照开始页排序
        sql += " order by pageStartIndex ";
        return seasonDao.findPage(BookCatalogListVO.class, bookCatalogQueryDTO.getPageNo(),
                bookCatalogQueryDTO.getPageSize(), params, sql);
    }

    /**
     * 全部查询电子书目录
     */
    public List<BookCatalogListVO> queryList(BookCatalogQueryDTO bookCatalogQueryDTO) {
        Map<String, Object> params = new HashMap<>();
        String sql = " select * from " + BookCatalog.TABLE_NAME + " t where isDelete = 0 ";
        if (StrKit.isNotEmpty(bookCatalogQueryDTO.getId())) {
            sql += " and t.id = :id ";
            params.put("id", bookCatalogQueryDTO.getId());
        }
        if (StrKit.isNotEmpty(bookCatalogQueryDTO.getBookId())) {
            sql += " and t.bookId = :bookId ";
            params.put("bookId", bookCatalogQueryDTO.getBookId());
        }
        if (StrKit.isNotEmpty(bookCatalogQueryDTO.getIntroduction())) {
            sql += " and t.introduction = :introduction ";
            params.put("introduction", bookCatalogQueryDTO.getIntroduction());
        }
        if (StrKit.isNotEmpty(bookCatalogQueryDTO.getPageStartIndex())) {
            sql += " and t.pageStartIndex = :pageStartIndex ";
            params.put("pageStartIndex", bookCatalogQueryDTO.getPageStartIndex());
        }
        if (StrKit.isNotEmpty(bookCatalogQueryDTO.getPageEndIndex())) {
            sql += " and t.pageEndIndex = :pageEndIndex ";
            params.put("pageEndIndex", bookCatalogQueryDTO.getPageEndIndex());
        }
        if (StrKit.isNotEmpty(bookCatalogQueryDTO.getTitleName())) {
            sql += " and t.titleName = :titleName ";
            params.put("titleName", bookCatalogQueryDTO.getTitleName());
        }
        //默认按照开始页排序
        sql += " order by pageStartIndex ";
        return seasonDao.find(BookCatalogListVO.class, params, sql);
    }

    /**
     * 根据电子书目录起始页,获取下一个目录的实体类
     * "startIndex"
     * "endIndex"
     * "bookId"
     * "type"
     */
    public BookCatalog getNextBookCatalogById(Map<String, Object> map) {
        StringBuilder sql = new StringBuilder(" select * from " + BookCatalog.TABLE_NAME + " where isDelete = 0 and bookId=:bookId ");
        Integer type = SafeKit.getInteger(map.get("type"));
        if (type == -1) {
            //向上排序
            sql.append(" and pageEndIndex < :startIndex order by pageEndIndex desc limit 1 ");
        } else if (type == 1) {
            //向下排序
            sql.append(" and pageStartIndex > :endIndex order by pageStartIndex limit 1 ");
        }
        return seasonDao.findFirst(BookCatalog.class, map, sql.toString());
    }

    /**
     * 根据bookId获取到所有目录实体类
     * @param bookId
     * @return
     */
    public List<BookCatalog> getCatalogListByBookId(Integer bookId) {
        String sql = " select * from " + BookCatalog.TABLE_NAME + " where isDelete=0 and bookId=:bookId order by pageStartIndex ";
        return seasonDao.find(BookCatalog.class, sql, bookId);
    }
}