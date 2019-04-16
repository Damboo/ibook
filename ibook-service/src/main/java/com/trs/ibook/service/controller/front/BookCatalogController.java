package com.trs.ibook.service.controller.front;

import com.season.common.StrKit;
import com.season.core.Page;
import com.season.core.Result;
import com.trs.ibook.core.exception.IBookParamException;
import com.trs.ibook.service.api.BookCatalogAPI;
import com.trs.ibook.service.dto.BookCatalogAddDTO;
import com.trs.ibook.service.dto.BookCatalogQueryDTO;
import com.trs.ibook.service.dto.BookCatalogUpdateDTO;
import com.trs.ibook.service.pojo.BookCatalog;
import com.trs.ibook.service.service.BookCatalogCRUDService;
import com.trs.ibook.service.vo.BookCatalogListVO;
import com.trs.ibook.service.vo.BookCatalogShowVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Title:
 * Description:
 * Copyright: 2019 北京拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company:北京拓尔思信息技术股份有限公司(TRS)
 * Project: ibook
 * Author: KylerTien
 * Create Time:19-4-4 16:38
 */
@RestController
@RequestMapping("/bookCatalog")
public class BookCatalogController implements BookCatalogAPI {

    @Autowired
    private BookCatalogCRUDService bookCatalogCRUDService;

    @Override
    @PostMapping(value = "/save")
    public Result<Integer> save(@Valid @RequestBody BookCatalogAddDTO bookCatalogAddDTO) {
        Result<Integer> result = Result.success();
        Map<String, Object> map = new HashMap<>();
        map.put("pageStartIndex", bookCatalogAddDTO.getPageStartIndex());
        map.put("pageEndIndex", bookCatalogAddDTO.getPageEndIndex());
        map.put("bookId", bookCatalogAddDTO.getBookId());
        //校验页码格式
        String errorMsg = bookCatalogCRUDService.checkCatalogPage(map);
        if (StrKit.isNotEmpty(errorMsg)) {
            result.setIsSuccess(false);
            result.setResultMsg(errorMsg);
            return result;
        }
        BookCatalog bookCatalog = bookCatalogCRUDService.save(bookCatalogAddDTO, map);
        result.setData(bookCatalog.getId());
        return result;
    }

    @Override
    @PostMapping(value = "/update")
    public Result<Void> update(@Valid @RequestBody BookCatalogUpdateDTO bookCatalogUpdateDTO) {
        Result<Void> result = Result.success();
        Map<String, Object> map = new HashMap<>();
        map.put("pageStartIndex", bookCatalogUpdateDTO.getPageStartIndex());
        map.put("pageEndIndex", bookCatalogUpdateDTO.getPageEndIndex());
        map.put("bookId", bookCatalogUpdateDTO.getBookId());
        //校验页码格式
        String errorMsg = bookCatalogCRUDService.checkCatalogPage(map);
        if (StrKit.isNotEmpty(errorMsg)) {
            result.setIsSuccess(false);
            result.setResultMsg(errorMsg);
            return result;
        }
        bookCatalogCRUDService.update(bookCatalogUpdateDTO);
        return Result.success();
    }

    @Override
    @PostMapping(value = "/page")
    public Result<Page<BookCatalogListVO>> page(@Valid @RequestBody BookCatalogQueryDTO bookCatalogQueryDTO) {
        Page<BookCatalogListVO> page = bookCatalogCRUDService.page(bookCatalogQueryDTO);
        Result<Page<BookCatalogListVO>> result = Result.success();
        result.setData(page);
        return result;
    }

    @Override
    @PostMapping(value = "/list")
    public Result<List<BookCatalogListVO>> list(@Valid @RequestBody BookCatalogQueryDTO bookCatalogQueryDTO) {
        List<BookCatalogListVO> page = bookCatalogCRUDService.list(bookCatalogQueryDTO);
        Result<List<BookCatalogListVO>> result = Result.success();
        result.setData(page);
        return result;
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, value = "show")
    public Result<BookCatalogShowVO> show(Integer id) {
        BookCatalogShowVO bookCatalogShowVO = bookCatalogCRUDService.show(id);
        Result<BookCatalogShowVO> result = Result.success();
        result.setData(bookCatalogShowVO);
        return result;
    }


    @Override
    @DeleteMapping(value = "/{id}")
    public Result<Integer> delete(@PathVariable Integer id) {
        int count = bookCatalogCRUDService.delete(id);
        Result<Integer> result = Result.success();
        result.setData(count);
        return result;
    }

    @Override
    @PostMapping(value = "/sort")
    public Result<Void> sort(Integer id, Integer type) {
        if (type != -1 && type != 1) {
            throw new IBookParamException("排序参数错误");
        }
        bookCatalogCRUDService.sort(id, type);
        return Result.success();
    }
}
