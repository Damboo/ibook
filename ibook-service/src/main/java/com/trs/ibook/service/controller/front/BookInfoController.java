package com.trs.ibook.service.controller.front;

import com.season.common.SafeKit;
import com.season.core.Page;
import com.season.core.Result;
import com.trs.ibook.service.api.BookInfoAPI;
import com.trs.ibook.service.dto.BookInfoAddDTO;
import com.trs.ibook.service.dto.BookInfoQueryDTO;
import com.trs.ibook.service.dto.BookInfoUpdateDTO;
import com.trs.ibook.service.service.BookInfoCRUDService;
import com.trs.ibook.service.vo.BookInfoListVO;
import com.trs.ibook.service.vo.BookInfoShowVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
 * Create Time:19-3-28 15:35
 */
@RestController
@RequestMapping("/bookInfo")
public class BookInfoController implements BookInfoAPI {

    @Autowired
    private BookInfoCRUDService bookInfoCRUDService;

    @Override
    @PostMapping(value = "/save")
    public Result<Map<String, Object>> save(@Valid @RequestBody BookInfoAddDTO bookInfoAddDTO, @RequestParam("file") MultipartFile file) {
        int bookId = bookInfoCRUDService.save(bookInfoAddDTO);
        Result<Map<String, Object>> result = Result.success();
        //返回map
        Map<String, Object> map;
        if (bookId == 0) {
            result.setIsSuccess(false);
            result.setResultMsg("创建目录失败,请检查服务器");
        } else {
            //保存成功,调用上传文件服务
            map = bookInfoCRUDService.uploadPDF(file, bookId);
            if (SafeKit.getBoolean(map.get("isSuccess"))) {
                //如果成功, 确定返回值信息
                result.setData(map);
                //上传文件失败
            } else {
                result.setResultMsg(SafeKit.getString(map.get("resultMsg")));
            }
            //移除成功标志
            map.remove("isSuccess");
        }
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

    @Override
    @ResponseBody
    @PostMapping(value = "downloadPDF")
    public Result<Integer> downloadPDF(Integer id) {
        boolean flag = bookInfoCRUDService.downloadPDF(id);
        Result<Integer> result = Result.success();
        if (!flag) {
            result.setIsSuccess(false);
            result.setResultMsg("当前电子书无内容,请检查");
        }
        return result;
    }

    @Override
    @ResponseBody
    @PostMapping(value = "cutPDF")
    public Result<Void> cutPDF(String pdfUrl, Integer bookId) {
        Result<Void> result = Result.success();
        boolean flag = bookInfoCRUDService.cutPDF(pdfUrl, bookId);
        return result;
    }
}
