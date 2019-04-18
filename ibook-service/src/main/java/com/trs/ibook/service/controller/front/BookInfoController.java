package com.trs.ibook.service.controller.front;

import com.season.common.SafeKit;
import com.season.common.StrKit;
import com.season.core.Page;
import com.season.core.Result;
import com.trs.ibook.core.exception.IBookParamException;
import com.trs.ibook.service.api.BookInfoAPI;
import com.trs.ibook.service.dto.BookInfoAddDTO;
import com.trs.ibook.service.dto.BookInfoQueryDTO;
import com.trs.ibook.service.dto.BookInfoUpdateDTO;
import com.trs.ibook.service.service.BookInfoCRUDService;
import com.trs.ibook.service.service.BookPictureCRUDService;
import com.trs.ibook.service.service.PDFToOriginService;
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
    @Autowired
    private PDFToOriginService pdfToOriginService;
    @Autowired
    private BookPictureCRUDService bookPictureCRUDService;

    @Override
    @PostMapping(value = "/save")
    public Result<Integer> save(@Valid @RequestBody BookInfoAddDTO bookInfoAddDTO) {
        int bookId = bookInfoCRUDService.save(bookInfoAddDTO);
        Result<Integer> result = Result.success();
        if (bookId == 0) {
            result.setIsSuccess(false);
            result.setResultMsg("创建目录失败,请检查服务器");
        }
        result.setData(bookId);
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
    @PostMapping(value = "uploadPDF")
    public Result<Map<String, Object>> uploadPDF(@RequestParam("file") MultipartFile file, Integer id) {
        Result<Map<String, Object>> result = Result.success();
        Map<String, Object> map = bookInfoCRUDService.uploadPDF(file, id);
        if (!SafeKit.getBoolean(map.get("isSuccess"))) {
            result.setIsSuccess(false);
            result.setResultMsg(SafeKit.getString(map.get("resultMsg")));
            return result;
        }
        Map<String, Object> returnMap = new HashMap<>();
        returnMap.put("siteId", map.get("siteId"));
        returnMap.put("bookId", map.get("bookId"));
        returnMap.put("pdfUrl", map.get("pdfUrl"));
        result.setData(returnMap);
        return result;
    }

    @Override
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
    @PostMapping(value = "cutPDF")
    public Result<Void> cutPDF(String pdfUrl, Integer bookId, Integer startBlankNum, Integer endBlankNum, Integer source) {
        Result<Void> result = Result.success();
        //首先判断是否存在这本书的页码和原图,进行逻辑删除
        bookPictureCRUDService.deleteOldPic(bookId);
        //开始进行生产者消费者模式切图
        String errorMsg = pdfToOriginService.cutPDF(pdfUrl, bookId, startBlankNum, endBlankNum);
        if (StrKit.isNotEmpty(errorMsg)) {
            //如果是新增来源(0),删除表记录,文件
            if (source == 0) {
                bookInfoCRUDService.deleteBook(bookId);
                //如果是修改来源(1),恢复上次被删文件
            } else if (source == 1) {
                bookPictureCRUDService.recoverOldPic(bookId);
            }
            result.setResultMsg(errorMsg);
            result.setIsSuccess(false);
            return result;
        }
        return result;
    }

    @Override
    @PostMapping(value = "changeStatus")
    public Result<Void> changeStatus(Integer id, Integer type) {
        if (type != 1 && type != 2) {
            throw new IBookParamException("上下架参数错误");
        }
        bookInfoCRUDService.changeStatus(id, type);
        return Result.success();
    }
}
