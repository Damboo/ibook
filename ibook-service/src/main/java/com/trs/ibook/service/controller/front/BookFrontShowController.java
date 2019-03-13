package com.trs.ibook.service.controller.front;

import com.season.common.StrKit;
import com.season.core.Result;
import com.trs.ibook.service.api.BookFrontShowAPI;
import com.trs.ibook.service.exception.ServiceException;
import com.trs.ibook.service.service.BookFrontShowService;
import com.trs.ibook.service.vo.BookCatalogListVO;
import com.trs.ibook.service.vo.BookPicturePageVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Title: 电子书前端展示控制器
 * Description:
 * Copyright: 2019 拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company: 拓尔思信息技术股份有限公司(TRS)
 * Project: ibook
 * Author: RayeGong
 * Create Time: 2019-03-13 18:35
 */
@RestController
@RequestMapping("/bookFrontShow")
public class BookFrontShowController implements BookFrontShowAPI {

    private final BookFrontShowService bookFrontShowService;

    @Autowired
    public BookFrontShowController(BookFrontShowService bookFrontShowService) {
        this.bookFrontShowService = bookFrontShowService;
    }

    @Override
    @GetMapping(value = "bookCatalogList")
    public Result<List<BookCatalogListVO>> bookCatalogList(Integer bookId) {
        if (StrKit.isEmpty(bookId)) {
            throw new ServiceException("电子书ID不能为空");
        }
        List<BookCatalogListVO> bookCatalogVOList = bookFrontShowService.bookCatalogList(bookId);
        Result<List<BookCatalogListVO>> result = Result.success();
        result.setData(bookCatalogVOList);
        return result;
    }

    @Override
    @GetMapping(value = "bookPicturePage")
    public Result<BookPicturePageVO> bookPicturePage(Integer bookId, Integer serialNo) {
        if (StrKit.isEmpty(bookId)) {
            throw new ServiceException("电子书ID不能为空");
        }
        if (StrKit.isEmpty(serialNo)) {
            throw new ServiceException("序号不能为空");
        }
        BookPicturePageVO bookPicturePageVO = bookFrontShowService.bookPicturePage(bookId, serialNo);
        Result<BookPicturePageVO> result = Result.success();
        result.setData(bookPicturePageVO);
        return result;
    }


}
