package com.trs.ibook.service.service;

import com.season.core.Page;
import com.trs.ibook.core.exception.IBookException;
import com.trs.ibook.core.exception.IBookParamException;
import com.trs.ibook.service.dao.BookCatalogDAO;
import com.trs.ibook.service.dto.BookCatalogAddDTO;
import com.trs.ibook.service.dto.BookCatalogQueryDTO;
import com.trs.ibook.service.dto.BookCatalogUpdateDTO;
import com.trs.ibook.service.mapper.BookCatalogMapper;
import com.trs.ibook.service.pojo.BookCatalog;
import com.trs.ibook.service.vo.BookCatalogListVO;
import com.trs.ibook.service.vo.BookCatalogShowVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class BookCatalogCRUDService {
    @Autowired
    private BookCatalogDAO bookCatalogDAO;

    /**
     * 新增【电子书信息】
     */
    @Transactional
    public BookCatalog save(BookCatalogAddDTO bookCatalogAddDTO) {
        BookCatalog bookCatalog = BookCatalogMapper.INSTANCE.fromAddDTO(bookCatalogAddDTO);
        bookCatalogDAO.save(bookCatalog);
        return bookCatalog;
    }

    /**
     * 修改【地区信息】
     */
    @Transactional(rollbackFor = IBookParamException.class)
    public void update(BookCatalogUpdateDTO bookCatalogUpdateDTO) {
        Integer id = bookCatalogUpdateDTO.getId();
        BookCatalog bookCatalog = bookCatalogDAO.findById(id);
        if (bookCatalog == null) {
            throw new IBookParamException("id有误");
        }
        BookCatalogMapper.INSTANCE.setUpdateDTO(bookCatalog, bookCatalogUpdateDTO);
        bookCatalogDAO.update(bookCatalog);
    }

    /**
     * 查询分页列表
     */
    public Page<BookCatalogListVO> page(BookCatalogQueryDTO bookCatalogQueryDTO) {
        return bookCatalogDAO.findByQuery(bookCatalogQueryDTO);
    }

    /**
     * 查询列表
     */
    public List<BookCatalogListVO> list(BookCatalogQueryDTO bookCatalogQueryDTO) {
        return bookCatalogDAO.queryList(bookCatalogQueryDTO);
    }

    /**
     * 查询【目录信息】详情
     *
     * @param id
     * @return
     */
    public BookCatalogShowVO show(Integer id) {
        BookCatalog bookCatalog = bookCatalogDAO.findById(id);
        if (bookCatalog == null) {
            throw new IBookParamException("未查询到记录");
        }
        return BookCatalogMapper.INSTANCE.toShowVO(bookCatalog);
    }

    /**
     * 删除【目录信息】
     *
     * @param id
     * @return
     */
    @Transactional(rollbackFor = IBookException.class)
    public int delete(Integer id) {
        return bookCatalogDAO.delete(id);
    }

}