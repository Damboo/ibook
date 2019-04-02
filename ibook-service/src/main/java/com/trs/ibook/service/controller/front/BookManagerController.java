package com.trs.ibook.service.controller.front;

import com.season.core.Result;
import com.trs.ibook.service.api.BookManagerAPI;
import com.trs.ibook.service.pojo.BookInfo;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Title:
 * Description:
 * Copyright: 2019 北京拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company:北京拓尔思信息技术股份有限公司(TRS)
 * Project: ibook
 * Author: KylerTien
 * Create Time:19-3-28 15:35
 */
@RestController
@RequestMapping("/bookFrontShow")
public class BookManagerController implements BookManagerAPI {
    @Override
    public Result<Void> addBook(BookInfo bookInfo) {
        return null;
    }

    @Override
    public Result<BookInfo> editBook(Integer bookId) {
        return null;
    }

    @Override
    public Result<Void> updateBook(BookInfo bookInfo) {
        return null;
    }
}
