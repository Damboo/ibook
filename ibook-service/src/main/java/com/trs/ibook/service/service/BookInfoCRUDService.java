package com.trs.ibook.service.service;

import com.season.common.StrKit;
import com.season.core.Page;
import com.trs.ibook.core.exception.IBookException;
import com.trs.ibook.core.exception.IBookParamException;
import com.trs.ibook.service.dao.BookInfoDAO;
import com.trs.ibook.service.dao.BookPictureDAO;
import com.trs.ibook.service.dto.BookInfoAddDTO;
import com.trs.ibook.service.dto.BookInfoQueryDTO;
import com.trs.ibook.service.dto.BookInfoUpdateDTO;
import com.trs.ibook.service.mapper.BookInfoMapper;
import com.trs.ibook.service.pojo.BookInfo;
import com.trs.ibook.service.util.ImageUtil;
import com.trs.ibook.service.vo.BookInfoListVO;
import com.trs.ibook.service.vo.BookInfoShowVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
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
    @Autowired
    private BookPictureDAO bookPictureDAO;

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

    /**
     * 导出PDF
     */
    public boolean loadPDF(Integer id, String baseDir, String oppositeDir) {
        //首先检查服务器是否存在PDF
        BookInfo bookInfo = bookInfoDAO.findById(id);
        //不为空, 直接下载
        if (StrKit.isNotEmpty(bookInfo.getPdfUrl())) {
            //TODO 服务器下载本地
            return true;
        } else {
            //获取当前bookId的服务器存储路径文件夹
            String albumName = bookPictureDAO.getBookUrlByBookId(id);
            if (StrKit.isEmpty(albumName)) {
                return false;
            }
            //构建原始图片路径
            String originPath = baseDir + albumName + "/origin/";
            //获取当前桌面路径
            File desktopDir = FileSystemView.getFileSystemView().getHomeDirectory();
            String desktopPath = desktopDir.getAbsolutePath();
            ImageUtil.buildPDF(originPath, desktopPath);
            //同时备份到服务器, 获取服务器路径
            String pdfPath = baseDir + albumName + File.separator + albumName + ".pdf";
            ImageUtil.buildPDF(originPath, pdfPath);
            bookInfo.setPdfUrl(oppositeDir + albumName + File.separator + albumName + ".pdf");
            return true;
        }

    }
}