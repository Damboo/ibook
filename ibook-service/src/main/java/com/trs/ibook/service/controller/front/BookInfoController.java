package com.trs.ibook.service.controller.front;

import com.season.core.Page;
import com.season.core.Result;
import com.trs.ibook.service.api.BookInfoAPI;
import com.trs.ibook.service.dto.BookInfoAddDTO;
import com.trs.ibook.service.dto.BookInfoQueryDTO;
import com.trs.ibook.service.dto.BookInfoUpdateDTO;
import com.trs.ibook.service.pojo.BookInfo;
import com.trs.ibook.service.service.BookInfoCRUDService;
import com.trs.ibook.service.vo.BookInfoListVO;
import com.trs.ibook.service.vo.BookInfoShowVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
@RequestMapping("/bookInfo")
public class BookInfoController implements BookInfoAPI {

    @Autowired
    private BookInfoCRUDService bookInfoCRUDService;

    @Override
    @PostMapping(value = "/save")
    public Result<Integer> save(@Valid @RequestBody BookInfoAddDTO bookInfoAddDTO) {
        BookInfo bookInfo = bookInfoCRUDService.save(bookInfoAddDTO);
        Result<Integer> result = Result.success();
        result.setData(bookInfo.getId());
        return result;
    }

    @Override
    @PostMapping(value = "/update")
    public Result<Void> update(@Valid @RequestBody BookInfoUpdateDTO bookInfoUpdateDTO) {
        bookInfoCRUDService.update(bookInfoUpdateDTO);
        return Result.success();
    }

    @Override
    @PostMapping(value = "/page")
    public Result<Page<BookInfoListVO>> page(@Valid @RequestBody BookInfoQueryDTO bookInfoQueryDTO) {
        Page<BookInfoListVO> page = bookInfoCRUDService.page(bookInfoQueryDTO);
        Result<Page<BookInfoListVO>> result = Result.success();
        result.setData(page);
        return result;
    }

    @Override
    @PostMapping(value = "/list")
    public Result<List<BookInfoListVO>> list(@Valid @RequestBody BookInfoQueryDTO bookInfoQueryDTO) {
        List<BookInfoListVO> page = bookInfoCRUDService.list(bookInfoQueryDTO);
        Result<List<BookInfoListVO>> result = Result.success();
        result.setData(page);
        return result;
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, value = "show")
    public Result<BookInfoShowVO> show(Integer id) {
        BookInfoShowVO bookInfoShowVO = bookInfoCRUDService.show(id);
        Result<BookInfoShowVO> result = Result.success();
        result.setData(bookInfoShowVO);
        return result;
    }


    @Override
    @DeleteMapping(value = "/{id}")
    public Result<Integer> delete(@PathVariable Integer id) {
        int count = bookInfoCRUDService.delete(id);
        Result<Integer> result = Result.success();
        result.setData(count);
        return result;
    }
}
