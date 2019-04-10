package com.trs.ibook.service.service;

import com.season.core.Page;
import com.trs.ibook.core.exception.IBookException;
import com.trs.ibook.core.exception.IBookParamException;
import com.trs.ibook.service.dao.BookInfoDAO;
import com.trs.ibook.service.dto.BookInfoAddDTO;
import com.trs.ibook.service.dto.BookInfoQueryDTO;
import com.trs.ibook.service.dto.BookInfoUpdateDTO;
import com.trs.ibook.service.mapper.BookInfoMapper;
import com.trs.ibook.service.pojo.BookInfo;
import com.trs.ibook.service.vo.BookInfoListVO;
import com.trs.ibook.service.vo.BookInfoShowVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * Title:
 * Description:
 * Copyright: 2019 北京拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company:北京拓尔思信息技术股份有限公司(TRS)
 * Project: ibook
 * Author: KylerTien
 * Create Time:19-4-3 17:44
 */

@Service
public class BookInfoCRUDService {

    @Autowired
    private BookInfoDAO bookInfoDAO;

    /**
     * 新增【电子书信息】
     */
    @Transactional
    public BookInfo save(BookInfoAddDTO bookInfoAddDTO) {
        BookInfo bookInfo = BookInfoMapper.INSTANCE.fromAddDTO(bookInfoAddDTO);
        bookInfo.setIsDelete(0);
        bookInfo.setStatus(1);
        bookInfo.setCreateTime(new Date());
        bookInfoDAO.save(bookInfo);
        return bookInfo;
    }

    /**
     * 修改【电子书信息】
     */
    @Transactional(rollbackFor = IBookParamException.class)
    public void update(BookInfoUpdateDTO bookInfoUpdateDTO) {
        Integer id = bookInfoUpdateDTO.getId();
        BookInfo bookInfo = bookInfoDAO.findById(id);
        if (bookInfo == null) {
            throw new IBookParamException("id有误");
        }
        BookInfoMapper.INSTANCE.setUpdateDTO(bookInfo, bookInfoUpdateDTO);
        bookInfoDAO.update(bookInfo);
    }

    /**
     * 查询分页列表
     */
    public Page<BookInfoListVO> page(BookInfoQueryDTO bookInfoQueryDTO) {
        return bookInfoDAO.findByQuery(bookInfoQueryDTO);
    }

    /**
     * 查询列表
     */
    public List<BookInfoListVO> list(BookInfoQueryDTO bookInfoQueryDTO) {
        return bookInfoDAO.queryList(bookInfoQueryDTO);
    }

    /**
     * 查询【电子书信息】详情
     */
    public BookInfoShowVO show(Integer id) {
        BookInfo bookInfo = bookInfoDAO.findById(id);
        if (bookInfo == null) {
            throw new IBookParamException("未查询到记录");
        }
        return BookInfoMapper.INSTANCE.toShowVO(bookInfo);
    }

    /**
     * 删除【电子书信息】
     */
    @Transactional(rollbackFor = IBookException.class)
    public int delete(Integer id) {
        return bookInfoDAO.delete(id);
    }

}