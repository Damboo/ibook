package com.trs.ibook.service.dao;

import com.season.common.SafeKit;
import com.season.common.StrKit;
import com.season.core.Page;
import com.trs.ibook.core.dao.AbstractDAO;
import com.trs.ibook.service.dto.BookPictureQueryDTO;
import com.trs.ibook.service.pojo.BookPicture;
import com.trs.ibook.service.vo.BookPictureListVO;
import com.trs.ibook.service.vo.BookPicturePageVO;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
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
     * 根据电子书ID查询改书物理总页数
     *
     * @param bookId
     * @return
     */
    public Integer getPictureCountByBookId(Integer bookId) {
        Map<String, Object> params = new HashMap<>();
        params.put("bookId", bookId);
        String sql = "SELECT COUNT(id) AS serialTotal FROM " + BookPicture.TABLE_NAME + " WHERE bookId = :bookId AND isDelete = 0 ";
        Map<String, Object> resultMap = seasonDao.queryFirst(params, sql);
        return SafeKit.getInteger(resultMap.get("serialTotal"));
    }


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
        String sql = "SELECT id,bookId,picUrl,pageIndex,serialNo FROM " + BookPicture.TABLE_NAME + " " +
                "WHERE bookId = :bookId AND serialNo = :serialNo AND isDelete = 0 ";
        return seasonDao.findFirst(BookPicturePageVO.class, params, sql);
    }

    /**
     * 查询电子相册所有页
     */
    public List<BookPicturePageVO> getAllBookPicture(Integer bookId) {
        String sql = " SELECT id,bookId,picUrl,pageIndex,serialNo from " + BookPicture.TABLE_NAME +
                " where bookId = ? AND isDelete = 0 order by serialNo ";
        return seasonDao.find(BookPicturePageVO.class, sql, bookId);
    }

    /**
     * 保存记录
     */
    public BookPicture saveBookPicture(BookPicture bookPicture){
        return seasonDao.save(bookPicture);
    }

    public BookPicture getBookPictureById(Integer id) {
        String sql = "SELECT * FROM " + BookPicture.TABLE_NAME + " WHERE id =? AND isDelete = 0 ";
        return seasonDao.findFirst(BookPicture.class, sql, id);
    }

    /**
     * 分页查询电子书
     */
    public Page<BookPictureListVO> findByQuery(BookPictureQueryDTO bookPictureQueryDTO) {
        Map<String, Object> params = new HashMap<>();
        String sql = "select * from " + BookPicture.TABLE_NAME + " t where isDelete = 0 ";
        if (StrKit.isNotEmpty(bookPictureQueryDTO.getId())) {
            sql += "and t.id = :id ";
            params.put("id", bookPictureQueryDTO.getId());
        }
        if (StrKit.isNotEmpty(bookPictureQueryDTO.getBookId())) {
            sql += "and t.bookId = :bookId ";
            params.put("bookId", bookPictureQueryDTO.getBookId());
        }
        if (StrKit.isNotEmpty(bookPictureQueryDTO.getCatalogId())) {
            sql += "and t.catalogId = :catalogId ";
            params.put("catalogId", bookPictureQueryDTO.getCatalogId());
        }
        return seasonDao.findPage(BookPictureListVO.class, bookPictureQueryDTO.getPageNo(),
                bookPictureQueryDTO.getPageSize(), params, sql);
    }

    /**
     * 分页查询电子书
     */
    public List<BookPictureListVO> queryList(BookPictureQueryDTO bookPictureQueryDTO) {
        Map<String, Object> params = new HashMap<>();
        String sql = "select * from " + BookPicture.TABLE_NAME + " t where isDelete = 0 ";
        if (StrKit.isNotEmpty(bookPictureQueryDTO.getId())) {
            sql += "and t.id = :id ";
            params.put("id", bookPictureQueryDTO.getId());
        }
        if (StrKit.isNotEmpty(bookPictureQueryDTO.getBookId())) {
            sql += "and t.bookId = :bookId ";
            params.put("bookId", bookPictureQueryDTO.getBookId());
        }
        if (StrKit.isNotEmpty(bookPictureQueryDTO.getCatalogId())) {
            sql += "and t.catalogId = :catalogId ";
            params.put("catalogId", bookPictureQueryDTO.getCatalogId());
        }
        return seasonDao.find(BookPictureListVO.class, params, sql);
    }
}
