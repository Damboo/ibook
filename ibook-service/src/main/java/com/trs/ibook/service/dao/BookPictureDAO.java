package com.trs.ibook.service.dao;

import com.season.common.SafeKit;
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

}
