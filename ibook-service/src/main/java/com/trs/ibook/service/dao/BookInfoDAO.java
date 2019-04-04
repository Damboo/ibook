package com.trs.ibook.service.dao;

import com.season.common.StrKit;
import com.season.core.Page;
import com.trs.ibook.core.dao.AbstractDAO;
import com.trs.ibook.service.dto.BookInfoQueryDTO;
import com.trs.ibook.service.pojo.BookInfo;
import com.trs.ibook.service.vo.BookInfoListVO;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
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

    /**
     * 分页查询电子书
     */
    public Page<BookInfoListVO> findByQuery(BookInfoQueryDTO bookInfoQueryDTO) {
        Map<String, Object> params = new HashMap<>();
        String sql = "select * from " + BookInfo.TABLE_NAME + " t where isDelete = 0 ";
        if (StrKit.isNotEmpty(bookInfoQueryDTO.getId())) {
            sql += "and t.id = :id ";
            params.put("id", bookInfoQueryDTO.getId());
        }
        if (StrKit.isNotEmpty(bookInfoQueryDTO.getAuthor())) {
            sql += "and t.author = :author ";
            params.put("regionCode", bookInfoQueryDTO.getAuthor());
        }
        if (StrKit.isNotEmpty(bookInfoQueryDTO.getSiteId())) {
            sql += "and t.siteId = :siteId ";
            params.put("siteId", bookInfoQueryDTO.getSiteId());
        }
        if (StrKit.isNotEmpty(bookInfoQueryDTO.getTitleName())) {
            sql += "and t.titleName = :titleName ";
            params.put("titleName", bookInfoQueryDTO.getTitleName());
        }
        if (StrKit.isNotEmpty(bookInfoQueryDTO.getStatus())) {
            sql += "and t.status = :status ";
            params.put("status", bookInfoQueryDTO.getStatus());
        }
        return seasonDao.findPage(BookInfoListVO.class, bookInfoQueryDTO.getPageNo(),
                bookInfoQueryDTO.getPageSize(), params, sql);
    }

    /**
     * 分页查询电子书
     */
    public List<BookInfoListVO> queryList(BookInfoQueryDTO bookInfoQueryDTO) {
        Map<String, Object> params = new HashMap<>();
        String sql = "select * from " + BookInfo.TABLE_NAME + " t where isDelete = 0 ";
        if (StrKit.isNotEmpty(bookInfoQueryDTO.getId())) {
            sql += "and t.id = :id ";
            params.put("id", bookInfoQueryDTO.getId());
        }
        if (StrKit.isNotEmpty(bookInfoQueryDTO.getAuthor())) {
            sql += "and t.author = :author ";
            params.put("regionCode", bookInfoQueryDTO.getAuthor());
        }
        if (StrKit.isNotEmpty(bookInfoQueryDTO.getSiteId())) {
            sql += "and t.siteId = :siteId ";
            params.put("siteId", bookInfoQueryDTO.getSiteId());
        }
        if (StrKit.isNotEmpty(bookInfoQueryDTO.getTitleName())) {
            sql += "and t.titleName = :titleName ";
            params.put("titleName", bookInfoQueryDTO.getTitleName());
        }
        if (StrKit.isNotEmpty(bookInfoQueryDTO.getStatus())) {
            sql += "and t.status = :status ";
            params.put("status", bookInfoQueryDTO.getStatus());
        }
        return seasonDao.find(BookInfoListVO.class, sql, params);
    }


}
