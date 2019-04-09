package com.trs.ibook.service.mapper;

import com.trs.ibook.service.dto.BookPictureAddDTO;
import com.trs.ibook.service.dto.BookPictureUpdateDTO;
import com.trs.ibook.service.pojo.BookPicture;
import com.trs.ibook.service.vo.BookPictureShowVO;
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
 * Create Time:19-4-9 14:23
 */
@Mapper
public interface BookPictureMapper {

    BookPictureMapper INSTANCE = Mappers.getMapper(BookPictureMapper.class);

    /**
     * addDTO映射pojo
     */
    BookPicture fromAddDTO(BookPictureAddDTO bookPictureAddDTO);

    /**
     * 将updateDTO的值映射到pojo
     */
    void setUpdateDTO(@MappingTarget BookPicture bookPicture, BookPictureUpdateDTO bookPictureUpdateDTO);

    /**
     * pojo映射vo
     */
    BookPictureShowVO toShowVO(BookPicture bookPicture);
}

