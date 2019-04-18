package com.trs.ibook.service.dao;

import com.season.common.SafeKit;
import com.trs.ibook.core.dao.AbstractDAO;
import com.trs.ibook.service.pojo.OriginPic;
import org.springframework.stereotype.Repository;

import java.util.Map;

/**
 * Title:
 * Description:原始图片数据操作
 * Copyright: 2019 北京拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company:北京拓尔思信息技术股份有限公司(TRS)
 * Project: ibook
 * Author: KylerTien
 * Create Time:19-4-3 10:17
 */
@Repository
public class OriginPicDAO extends AbstractDAO<OriginPic> {

    /**
     * 根据bookId获取下一页SerialNo
     */
    public int getNewSerialNo(Integer bookId) {
        String sql = " select serialNo from " + OriginPic.TABLE_NAME + " where isDelete=0 and bookId=? order by serialNo desc limit 1 ";
        Map<String, Object> lastSerialNo = seasonDao.queryFirst(sql, bookId);
        return lastSerialNo == null || lastSerialNo.isEmpty() ? 1 : SafeKit.getInteger(lastSerialNo.get("serialNo")) + 1;
    }

    /**
     * 删除指定电子书的原始页(重新上传PDF时)
     */
    public void deleteByBookId(Integer bookId) {
        String sql = " update " + OriginPic.TABLE_NAME + " set isDelete=1 where bookId=? and isDelete=0 ";
        seasonDao.execute(sql, bookId);
    }

    /**
     * cutPDF失败,回滚操作,恢复已经删除指定电子书的原始页
     */
    public void recoverByBookId(Integer bookId) {
        String sql = " update " + OriginPic.TABLE_NAME + " set isDelete=0 where bookId=? and isDelete=1 ";
        seasonDao.execute(sql, bookId);
    }
}
