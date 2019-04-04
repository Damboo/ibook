package com.trs.ibook.service.mapper;

import com.trs.ibook.service.dto.BookInfoAddDTO;
import com.trs.ibook.service.dto.BookInfoUpdateDTO;
import com.trs.ibook.service.pojo.BookInfo;
import com.trs.ibook.service.vo.BookInfoShowVO;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

/**
 * Title:
 * Description:
 * Copyright: 2019 北京拓尔思信息技术股份有限公司 版权所有.保留所有权
 * Company:北京拓尔思信息技术股份有限公司(TRS)
 * Project: ibook
 * Author: KylerTien
 * Create Time:19-4-4 14:04
 */
@Mapper
public interface BookInfoMapper {

    BookInfoMapper INSTANCE = Mappers.getMapper(BookInfoMapper.class);

    /**
     * addDTO映射pojo
     */
    BookInfo fromAddDTO(BookInfoAddDTO bookInfoAddDTO);

    /**
     * 将updateDTO的值映射到pojo
     */
    void setUpdateDTO(BookInfo bookInfo, @MappingTarget BookInfoUpdateDTO bookInfoUpdateDTO);

    /**
     * pojo映射vo
     */
    BookInfoShowVO toShowVO(BookInfo bookInfo);
}
