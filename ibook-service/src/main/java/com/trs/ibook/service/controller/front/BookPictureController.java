package com.trs.ibook.service.controller.front;

import com.season.common.StrKit;
import com.season.core.Page;
import com.season.core.Result;
import com.trs.ibook.core.exception.IBookException;
import com.trs.ibook.core.exception.IBookParamException;
import com.trs.ibook.service.api.BookPictureAPI;
import com.trs.ibook.service.dto.*;
import com.trs.ibook.service.pojo.BookPicture;
import com.trs.ibook.service.service.BookPictureCRUDService;
import com.trs.ibook.service.vo.BookPictureListVO;
import com.trs.ibook.service.vo.BookPictureShowVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Map;


/**
 * Title:
 * Description:
 * Copyright: 2019 北京拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company:北京拓尔思信息技术股份有限公司(TRS)
 * Project: ibook
 * Author: KylerTien
 * Create Time:19-3-29 10:55
 */
@RestController
@RequestMapping("/")
public class BookPictureController implements BookPictureAPI {

    @Autowired
    private BookPictureCRUDService bookPictureCRUDService;
    @Value("${ibook.service.imageUpload.baseDir}")
    private String baseDir;

    @Override
    @PostMapping(value = "/update")
    public Result<Void> update(@Valid @RequestBody BookPictureUpdateDTO bookPictureUpdateDTO) {
        bookPictureCRUDService.update(bookPictureUpdateDTO);
        return Result.success();
    }

    @Override
    @PostMapping(value = "/page")
    public Result<Map<String, Object>> page(@Valid @RequestBody BookPictureQueryDTO bookPictureQueryDTO) {
        Map<String, Object> map = bookPictureCRUDService.page(bookPictureQueryDTO);
        Result<Map<String, Object>> result = Result.success();
        result.setData(map);
        return result;
    }

    @Override
    @PostMapping(value = "/list")
    public Result<List<BookPictureListVO>> list(@Valid @RequestBody BookPictureQueryDTO bookPictureQueryDTO) {
        List<BookPictureListVO> page = bookPictureCRUDService.list(bookPictureQueryDTO);
        Result<List<BookPictureListVO>> result = Result.success();
        result.setData(page);
        return result;
    }

    @Override
    @RequestMapping(method = RequestMethod.GET, value = "show")
    public Result<BookPictureShowVO> show(Integer id) {
        BookPictureShowVO bookPictureShowVO = bookPictureCRUDService.show(id);
        Result<BookPictureShowVO> result = Result.success();
        result.setData(bookPictureShowVO);
        return result;
    }


    @Override
    @DeleteMapping(value = "/{id}")
    public Result<Integer> delete(@PathVariable Integer id) {
        int count = bookPictureCRUDService.delete(id);
        Result<Integer> result = Result.success();
        result.setData(count);
        return result;
    }

    @Override
    @RequestMapping(method = RequestMethod.POST, value = "imageUpload")
    public Result imageUpload(@RequestPart MultipartFile multipartFile, Integer bookId) {
        Result result = Result.success();
        if (multipartFile == null) {
            throw new IBookException("图片不能为空");
        }
        if (StrKit.isEmpty(bookId)) {
            throw new IBookParamException("电子书id不能为空");
        }
        try {
            bookPictureCRUDService.imageUpload(multipartFile, baseDir, bookId);
        } catch (IOException e) {
            result.setIsSuccess(false);
            return result;
        }
        return result;
    }
}
