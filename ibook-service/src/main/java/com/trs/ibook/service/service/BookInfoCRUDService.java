package com.trs.ibook.service.service;

import com.season.common.DateKit;
import com.season.common.StrKit;
import com.season.core.Page;
import com.trs.ibook.core.exception.IBookException;
import com.trs.ibook.core.exception.IBookParamException;
import com.trs.ibook.service.constant.BookConstant;
import com.trs.ibook.service.dao.BookInfoDAO;
import com.trs.ibook.service.dto.BookInfoAddDTO;
import com.trs.ibook.service.dto.BookInfoQueryDTO;
import com.trs.ibook.service.dto.BookInfoUpdateDTO;
import com.trs.ibook.service.mapper.BookInfoMapper;
import com.trs.ibook.service.pojo.BookInfo;
import com.trs.ibook.service.util.ImageUtil;
import com.trs.ibook.service.vo.BookInfoListVO;
import com.trs.ibook.service.vo.BookInfoShowVO;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
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
 * Create Time:19-4-3 17:44
 */

@Service
public class BookInfoCRUDService {

    @Autowired
    private BookInfoDAO bookInfoDAO;
    @Value("${ibook.service.imageUpload.baseDir}")
    private String baseDir;
    @Value("${ibook.service.imageUpload.oppositeDir}")
    private String oppositeDir;
    private static final Logger logger = Logger.getLogger(BookInfoCRUDService.class);

    /**
     * 新增【电子书信息】
     */
    @Transactional(rollbackFor = IBookParamException.class)
    public int save(BookInfoAddDTO bookInfoAddDTO) {
        BookInfo bookInfo = BookInfoMapper.INSTANCE.fromAddDTO(bookInfoAddDTO);
        bookInfo.setIsDelete(0);
        bookInfo.setStatus(1);
        bookInfo.setCreateTime(new Date());
        bookInfo = bookInfoDAO.save(bookInfo);
        int id = bookInfo.getId();
        StringBuilder bookPath = new StringBuilder(baseDir);
        StringBuilder locationName = new StringBuilder();
        //需要在服务器新开文件夹
        if (bookInfo.getSiteId() == BookConstant.LEADERSITEID) {
            locationName.append("leader_");
        } else if (bookInfo.getSiteId() == BookConstant.HAIERSITEID) {
            locationName.append("haier_");
        } else if (bookInfo.getSiteId() == BookConstant.CASARTESITEID) {
            locationName.append("casarte_");
        }
        locationName.append(DateKit.getDateStr(new Date(),"yyyyMMdd")).append("_").append(id);
        String bookPathStr = bookPath.append(locationName).toString();
        //检查目录
        File uploadDir = new File(bookPathStr);
        boolean bookFlag = uploadDir.mkdirs();
        boolean normalFlag = false;
        boolean smallFlag = false;
        if (bookFlag) {
            //创建目录成功,继续创建目录下的两个文件夹
            normalFlag = new File(bookPathStr + "/normal/").mkdirs();
            smallFlag = new File(bookPathStr + "/small/").mkdirs();
            //更新字段
            bookInfo.setLocationName(locationName.toString());
            bookInfoDAO.update(bookInfo);
        }
        boolean flag = bookFlag && normalFlag && smallFlag;
        if (!flag) {
            //创建目录失败,回滚操作
            bookInfoDAO.physicalDelete(id);
            return 0;
        }
        return bookInfo.getId();
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
     * 上传PDF
     */
    public Map<String, Object> uploadPDF(MultipartFile file, Integer id) {
        Map<String, Object> result = new HashMap<>();
        result.put("isSuccess", true);
        //根据id取到文件夹名称
        String albumName = bookInfoDAO.getLocationNameById(id);
        //PDF存储路径
        String pdfSavePath = baseDir + albumName + File.separator;
        //检查目录
        File uploadDir = new File(pdfSavePath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
        if (!uploadDir.isDirectory()) {
            result.put("isSuccess", false);
            result.put("resultMsg", "上传目录不存在");
            return result;
        }
        //检查目录写权限
        if (!uploadDir.canWrite()) {
            result.put("isSuccess", false);
            result.put("resultMsg", "上传目录没有写");
            return result;
        }
        String pdfName;
        if (!file.isEmpty()) {
            //获得文件后缀名称
            String extName = file.getOriginalFilename().substring(file.getOriginalFilename().indexOf('.') + 1).toLowerCase();
            if (!"pdf".equals(extName)) {
                result.put("isSuccess", false);
                result.put("resultMsg", "文件格式不正确");
                return result;
            }
            pdfName = albumName + "." + extName;
            //校验文件头
            try (InputStream inputStream = file.getInputStream()) {
                byte[] b = new byte[4];
                /*
                 * int read() 从此输入流中读取一个数据字节。 int read(byte[] b) 从此输入流中将最多 b.length
                 * 个字节的数据读入一个 byte 数组中。 int read(byte[] b, int off, int len)
                 * 从此输入流中将最多 len 个字节的数据读入一个 byte 数组中。
                 */
                inputStream.read(b, 0, b.length);
                StringBuilder builder = new StringBuilder();
                String hv;
                for (byte aB : b) {
                    // 以十六进制（基数 16）无符号整数形式返回一个整数参数的字符串表示形式，并转换为大写
                    hv = Integer.toHexString(aB & 0xFF).toUpperCase();
                    if (hv.length() < 2) {
                        builder.append(0);
                    }
                    builder.append(hv);
                }
                //判断是不是PDF类型
                if (!"25504446".equals(builder.toString())) {
                    result.put("isSuccess", false);
                    result.put("resultMsg", "文件格式不正确");
                    return result;
                }
                file.transferTo(new File(pdfSavePath + pdfName));
            } catch (IOException e) {
                logger.error("二进制转换错误!", e);
            }
            BookInfo bookInfo = bookInfoDAO.findById(id);
            result.put("siteId", bookInfo.getSiteId());
            result.put("bookId", bookInfo.getId());
            //PDF上传成功,那么写入字段
            String pdfUrl = oppositeDir + albumName + File.separator + pdfName;
            bookInfo.setPdfUrl(pdfUrl);
            bookInfoDAO.update(bookInfo);
            result.put("pdfUrl", pdfUrl);
        }
        return result;
    }

    /**
     * 导出PDF
     */
    public boolean downloadPDF(Integer id) {
        //首先检查服务器是否存在PDF
        BookInfo bookInfo = bookInfoDAO.findById(id);
        //不为空, 直接下载
        if (StrKit.isNotEmpty(bookInfo.getPdfUrl())) {
            //TODO 服务器下载本地
            return true;
        } else {
            //获取当前bookId的服务器存储路径文件夹
            String albumName = bookInfoDAO.getLocationNameById(id);
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