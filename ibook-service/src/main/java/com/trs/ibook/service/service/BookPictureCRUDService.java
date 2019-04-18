package com.trs.ibook.service.service;

import com.season.common.StrKit;
import com.trs.ibook.core.exception.IBookException;
import com.trs.ibook.core.exception.IBookParamException;
import com.trs.ibook.service.dao.*;
import com.trs.ibook.service.dao.BookPictureDAO;
import com.trs.ibook.service.dto.BookPictureQueryDTO;
import com.trs.ibook.service.dto.BookPictureUpdateDTO;
import com.trs.ibook.service.mapper.BookPictureMapper;
import com.trs.ibook.service.pojo.BookInfo;
import com.trs.ibook.service.pojo.BookPicture;
import com.trs.ibook.service.pojo.OriginPic;
import com.trs.ibook.service.util.ImageUtil;
import com.trs.ibook.service.vo.BookPictureListVO;
import com.trs.ibook.service.vo.BookPictureShowVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title: 图片上传服务
 * Description:
 * Copyright: 2017 北京拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company: 北京拓尔思信息技术股份有限公司(TRS)
 * Project: trs-interaction
 * Author: RayeGong
 * Create Time: 2018-01-29 10:30
 */
@Service
public class BookPictureCRUDService {

    @Autowired
    private BookInfoDAO bookInfoDAO;
    @Autowired
    private OriginPicDAO originPicDAO;
    @Autowired
    private BookPictureDAO bookPictureDAO;
    @Value("${ibook.service.imageUpload.frontDir}")
    private String frontDir;

    /**
     * 修改【电子书页码信息】
     */
    @Transactional(rollbackFor = IBookParamException.class)
    public void update(BookPictureUpdateDTO bookPictureUpdateDTO) {
        Integer id = bookPictureUpdateDTO.getId();
        BookPicture bookPicture = bookPictureDAO.findById(id);
        if (bookPicture == null) {
            throw new IBookParamException("id有误");
        }
        BookPictureMapper.INSTANCE.setUpdateDTO(bookPicture, bookPictureUpdateDTO);
        bookPictureDAO.update(bookPicture);
        //如果修改页码时,指定某页码的目录id为0,那么以当前页为差值,对下面所有页码重新排序
        if (0 == bookPictureUpdateDTO.getCatalogId()) {
            Map<String,Object> map = new HashMap<>();
            map.put("bookId",bookPicture.getBookId());
            map.put("difference",bookPicture.getPageIndex());
            bookPictureDAO.resetPageIndexByDifference(map);
        }
    }

    /**
     * 查询分页列表
     */
    public Map<String, Object> page(BookPictureQueryDTO bookPictureQueryDTO) {
        return bookPictureDAO.findByQuery(bookPictureQueryDTO);
    }

    /**
     * 查询列表
     */
    public List<BookPictureListVO> list(BookPictureQueryDTO bookPictureQueryDTO) {
        return bookPictureDAO.queryList(bookPictureQueryDTO);
    }

    /**
     * 查询【电子书页码信息】详情
     *
     * @param id
     * @return
     */
    public BookPictureShowVO show(Integer id) {
        BookPicture bookPicture = bookPictureDAO.findById(id);
        if (bookPicture == null) {
            throw new IBookParamException("未查询到记录");
        }
        return BookPictureMapper.INSTANCE.toShowVO(bookPicture);
    }

    /**
     * 删除【电子书页码信息】
     *
     * @param id
     * @return
     */
    @Transactional(rollbackFor = IBookException.class)
    public int delete(Integer id) {
        return bookPictureDAO.delete(id);
    }

    public void imageUpload(MultipartFile multipartFile, String baseDir, Integer bookId) throws IOException {
        //根据bookId获取对应的产品信息
        BookInfo bookInfo = bookInfoDAO.getBookInfoById(bookId);
        String albumName = bookInfo.getLocationName();
        //原始图片的存储路径
        String filePath = baseDir + albumName + "/origin/";
        //切割后的页码存储路径
        String pagePath = baseDir + albumName + "/normal/";
        String fileFullName = ImageUtil.upload(multipartFile, filePath, albumName);
        //上传图片后,原图存库
        OriginPic originPic = new OriginPic();
        originPic.setBookId(bookId);
        originPic.setCreateTime(new Date());
        originPic.setCreateUserId(null);
        originPic.setIsDelete(0);
        originPic.setPicUrl(fileFullName);
        originPic.setSerialNo(originPicDAO.getNewSerialNo(bookId));
        originPicDAO.save(originPic);
        //切割原图后, 取到两页路径
        String[] pagePart = ImageUtil.splitImage(fileFullName, pagePath);
        String part1 = pagePart[0].replace(frontDir, "");
        String part2 = pagePart[1].replace(frontDir, "");
        //页码存库
        BookPicture bookPicture = new BookPicture();
        bookPicture.setBookId(bookId);
        bookPicture.setCreateTime(new Date());
        bookPicture.setCreateUserId(null);
        bookPicture.setIsDelete(0);
        int serialNo = bookPictureDAO.getNewSerialNoByBookId(bookId);
        int pageIndex = bookPictureDAO.getNewPageIndexByBookId(bookId);
        bookPicture.setSerialNo(serialNo);
        bookPicture.setPageIndex(pageIndex);
        bookPicture.setPicUrl(part1);
        bookPictureDAO.save(bookPicture);
        bookPicture.setSerialNo(serialNo + 1);
        bookPicture.setPageIndex(pageIndex + 1);
        bookPicture.setPicUrl(part2);
        bookPictureDAO.save(bookPicture);
        //如果是第一页上传,并且没有封面,默认设置第一页为封面
        if (serialNo == 1 && StrKit.isEmpty(bookInfo.getCoverUrl())) {
            bookInfo.setCoverUrl(part1);
            bookInfoDAO.updateCoverUrl(bookInfo);
        }
    }

    /**
     * 电子书排序,-1表示向上排序,1表示向下排序
     */
    public void sort(Integer id, Integer type) {
        BookPicture bookPicture = bookPictureDAO.findById(id);
        Integer bookId = bookPicture.getBookId();
        Integer oldSerialNo = bookPicture.getSerialNo();
        Integer oldPageIndex = bookPicture.getPageIndex();
        bookPicture.setPageIndex(oldPageIndex + type);
        bookPicture.setSerialNo(oldSerialNo + type);
        //需要交换相邻的数据
        BookPicture preBookPicture = bookPictureDAO.getBookPictureByPage(bookId, oldPageIndex + type);
        preBookPicture.setPageIndex(oldPageIndex);
        preBookPicture.setSerialNo(oldSerialNo);
        bookPictureDAO.update(bookPicture);
        bookPictureDAO.update(preBookPicture);
    }

    /**
     * 判断指定书是否有历史书页,进行逻辑删除
     */
    public void deleteOldPic(Integer bookId) {
        originPicDAO.deleteByBookId(bookId);
        bookPictureDAO.deleteByBookId(bookId);
    }

    /**
     * 恢复已经删除的历史书页
     */
    public void recoverOldPic(Integer bookId){
        originPicDAO.recoverByBookId(bookId);
        bookPictureDAO.recoverByBookId(bookId);
    }
}
