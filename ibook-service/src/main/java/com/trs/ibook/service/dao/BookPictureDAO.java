package com.trs.ibook.service.dao;

import com.season.common.SafeKit;
import com.season.common.StrKit;
import com.season.core.Page;
import com.trs.ibook.core.dao.AbstractDAO;
import com.trs.ibook.service.dto.BookPictureQueryDTO;
import com.trs.ibook.service.pojo.BookCatalog;
import com.trs.ibook.service.pojo.BookPicture;
import com.trs.ibook.service.vo.BookPictureListVO;
import com.trs.ibook.service.vo.BookPicturePageVO;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
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
        String sql = "SELECT id,bookId,picUrl,pageIndex,serialNo FROM " + BookPicture.TABLE_NAME +
                " WHERE bookId = :bookId AND serialNo = :serialNo AND isDelete = 0 ";
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
     * 分页查询电子书
     */
    public Map<String, Object> findByQuery(BookPictureQueryDTO bookPictureQueryDTO) {
        Map<String, Object> params = new HashMap<>();
        Map<String, Object> returnMap = new HashMap<>();
        List<BookPictureListVO> dTOList = new ArrayList<>();
        String sql = "select * from " + BookPicture.TABLE_NAME + " where isDelete = 0 ";
        if (StrKit.isNotEmpty(bookPictureQueryDTO.getId())) {
            sql += "and id = :id ";
            params.put("id", bookPictureQueryDTO.getId());
        }
        if (StrKit.isNotEmpty(bookPictureQueryDTO.getBookId())) {
            sql += "and bookId = :bookId ";
            params.put("bookId", bookPictureQueryDTO.getBookId());
        }
        if (StrKit.isNotEmpty(bookPictureQueryDTO.getCatalogId())) {
            sql += "and catalogId = :catalogId ";
            params.put("catalogId", bookPictureQueryDTO.getCatalogId());
        }
        //默认按照物理页排序
        sql += " order by serialNo ";
        Page<BookPicture> page = seasonDao.findPage(BookPicture.class, bookPictureQueryDTO.getPageNo(),
                bookPictureQueryDTO.getPageSize(), params, sql);
        returnMap.put("entityCount", page.getEntityCount());
        returnMap.put("firstEntityIndex", page.getFirstEntityIndex());
        returnMap.put("lastEntityIndex", page.getLastEntityIndex());
        returnMap.put("pageCount", page.getPageCount());
        returnMap.put("pageNo", page.getPageNo());
        returnMap.put("pageSize", page.getPageSize());
        //对查询结果进行处理
        for (BookPicture entity : page.getEntities()) {
            BookPictureListVO oDto = new BookPictureListVO(entity);
            Integer catalogId = oDto.getCatalogId();
            if (catalogId > 0) {
                BookCatalog bookCatalog = seasonDao.findById(BookCatalog.class, catalogId);
                if (null != bookCatalog) {
                    oDto.setCatalogTitle(bookCatalog.getTitleName());
                }
            }
            dTOList.add(oDto);
        }
        returnMap.put("entities", dTOList);
        return returnMap;
    }

    /**
     * 全部查询电子书
     */
    public List<BookPictureListVO> queryList(BookPictureQueryDTO bookPictureQueryDTO) {
        Map<String, Object> params = new HashMap<>();
        List<BookPictureListVO> returnList = new ArrayList<>();
        String sql = "select * from " + BookPicture.TABLE_NAME + " where isDelete = 0 ";
        if (StrKit.isNotEmpty(bookPictureQueryDTO.getId())) {
            sql += "and id = :id ";
            params.put("id", bookPictureQueryDTO.getId());
        }
        if (StrKit.isNotEmpty(bookPictureQueryDTO.getBookId())) {
            sql += "and bookId = :bookId ";
            params.put("bookId", bookPictureQueryDTO.getBookId());
        }
        if (StrKit.isNotEmpty(bookPictureQueryDTO.getCatalogId())) {
            sql += "and catalogId = :catalogId ";
            params.put("catalogId", bookPictureQueryDTO.getCatalogId());
        }
        //默认按照物理页排序
        sql += " order by serialNo ";
        List<BookPicture> list = seasonDao.find(BookPicture.class, params, sql);
        for (BookPicture bookPicture : list) {
            BookPictureListVO oDto = new BookPictureListVO(bookPicture);
            Integer catalogId = oDto.getCatalogId();
            if (catalogId > 0) {
                BookCatalog bookCatalog = seasonDao.findById(BookCatalog.class, catalogId);
                if (null != bookCatalog) {
                    oDto.setCatalogTitle(bookCatalog.getTitleName());
                }
            }
            returnList.add(oDto);
        }
        return returnList;
    }

    /**
     * 根据bookId,pageIndex查询记录
     */
    public BookPicture getBookPictureByPage(Integer bookId, Integer pageIndex) {
        String sql = " select * from " + BookPicture.TABLE_NAME + " where bookId=? and pageIndex=? and isDelete = 0 ";
        return seasonDao.findFirst(BookPicture.class, sql, bookId, pageIndex);
    }

    /**
     * 根据bookId获取下一页的pageIndex
     */
    public int getNewPageIndexByBookId(Integer bookId) {
        String sql = " select pageIndex from " + BookPicture.TABLE_NAME + " where bookId=? and isDelete = 0 order by pageIndex desc limit 1";
        Map<String, Object> map = seasonDao.queryFirst(sql, bookId);
        return map == null || map.isEmpty() ? 1 : SafeKit.getInteger(map.get("pageIndex")) + 1;
    }

    /**
     * 根据bookId获取下一页的serialNo
     */
    public int getNewSerialNoByBookId(Integer bookId) {
        String sql = " select serialNo from " + BookPicture.TABLE_NAME + " where bookId=? and isDelete = 0 order by serialNo desc limit 1 ";
        Map<String, Object> map = seasonDao.queryFirst(sql, bookId);
        return map == null || map.isEmpty() ? 1 : SafeKit.getInteger(map.get("serialNo")) + 1;
    }

    /**
     * 判断是否有历史书页进行逻辑删除(重新上传PDF时)
     */
    public void deleteByBookId(Integer bookId) {
        String sql = " update " + BookPicture.TABLE_NAME + " set isDelete=1 where bookId=? and isDelete=0 ";
        seasonDao.execute(sql, bookId);
    }

    /**
     * 指定页码区间,修改对应的目录id
     */
    public void setCatalogIdByPageIndex(Map<String, Object> map) {
        String sql = " update " + BookPicture.TABLE_NAME + " set catalogId=:catalogId where isDelete=0 " +
                "and pageIndex>=:pageStartIndex and pageIndex<=:pageEndIndex and bookId=:bookId ";
        seasonDao.execute(map, sql);
    }

    /**
     * 获取最大结束页
     */
    public int getMaxEndIndexByBookId(Integer bookId) {
        String sql = " select * from " + BookPicture.TABLE_NAME + " where bookId=? and isDelete=0 order by pageIndex desc limit 1 ";
        BookPicture bookPicture = seasonDao.findFirst(BookPicture.class, sql, bookId);
        return bookPicture != null ? bookPicture.getPageIndex() : 0;
    }

    /**
     * 对指定页码后的所有页重新排序
     * 参数:书籍id,差值
     */
    public void resetPageIndexByDifference(Map<String, Object> map) {
        String sql = " update " + BookPicture.TABLE_NAME + " set pageIndex = pageIndex-:difference where isDelete=0 and bookId=:bookId and pageIndex>:difference ";
        seasonDao.execute(map, sql);
    }
}
