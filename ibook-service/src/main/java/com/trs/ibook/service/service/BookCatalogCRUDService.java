package com.trs.ibook.service.service;

import com.season.core.Page;
import com.trs.ibook.core.exception.IBookException;
import com.trs.ibook.core.exception.IBookParamException;
import com.trs.ibook.service.dao.BookCatalogDAO;
import com.trs.ibook.service.dao.BookInfoDAO;
import com.trs.ibook.service.dto.BookCatalogAddDTO;
import com.trs.ibook.service.dto.BookCatalogQueryDTO;
import com.trs.ibook.service.dto.BookCatalogUpdateDTO;
import com.trs.ibook.service.mapper.BookCatalogMapper;
import com.trs.ibook.service.pojo.BookCatalog;
import com.trs.ibook.service.pojo.BookInfo;
import com.trs.ibook.service.vo.BookCatalogListVO;
import com.trs.ibook.service.vo.BookCatalogShowVO;
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
public class BookCatalogCRUDService {

    @Autowired
    private BookCatalogDAO bookCatalogDAO;
    @Autowired
    private BookInfoDAO bookInfoDAO;

    /**
     * 新增【电子书信息】
     */
    @Transactional
    public BookCatalog save(BookCatalogAddDTO bookCatalogAddDTO) {
        BookCatalog bookCatalog = BookCatalogMapper.INSTANCE.fromAddDTO(bookCatalogAddDTO);
        bookCatalog.setCreateTime(new Date());
        bookCatalog.setIsDelete(0);
        bookCatalog.setCreateUserId(null);
        bookCatalog.setParentId(null == bookCatalog.getParentId() ? 0 : bookCatalog.getParentId());
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
        //根据目录找到对应的书籍id,置于下架
        BookInfo bookInfo = bookInfoDAO.findById(bookCatalog.getBookId());
        if (bookInfo == null) {
            throw new IBookParamException("当前目录无对应的书籍信息");
        }
        bookInfo.setStatus(2);
        //对应的书籍进行下架
        bookInfoDAO.update(bookInfo, "status");
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

    /**
     * 电子书排序,-1表示向上排序,1表示向下排序
     */
    public void sort(Integer id, Integer type) {
        BookCatalog bookCatalog = bookCatalogDAO.findById(id);
        if (null == bookCatalog) {
            throw new IBookParamException("无效的目录id");
        }
        Integer startIndex = bookCatalog.getPageStartIndex();
        Integer endIndex = bookCatalog.getPageEndIndex();
        //需要交换相邻的数据
        BookCatalog nextBookCatalog = bookCatalogDAO.getNextBookCatalogById(startIndex, type);
        if (nextBookCatalog == null) {
            throw new IBookParamException("当前目录已是最底或最顶");
        }
        Integer nextStartIndex = nextBookCatalog.getPageStartIndex();
        Integer nextEndIndex = nextBookCatalog.getPageEndIndex();
        bookCatalog.setPageStartIndex(nextStartIndex);
        bookCatalog.setPageEndIndex(nextEndIndex);
        nextBookCatalog.setPageStartIndex(startIndex);
        nextBookCatalog.setPageEndIndex(endIndex);
        bookCatalogDAO.update(bookCatalog);
        bookCatalogDAO.update(nextBookCatalog);
    }
}