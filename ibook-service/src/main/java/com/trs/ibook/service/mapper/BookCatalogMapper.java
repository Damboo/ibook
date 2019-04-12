package com.trs.ibook.service.mapper;

import com.trs.ibook.service.dto.BookCatalogAddDTO;
import com.trs.ibook.service.dto.BookCatalogUpdateDTO;
import com.trs.ibook.service.pojo.BookCatalog;
import com.trs.ibook.service.vo.BookCatalogShowVO;
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
 * @author dambo
 */
@Mapper
public interface BookCatalogMapper {

    BookCatalogMapper INSTANCE = Mappers.getMapper(BookCatalogMapper.class);

    /**
     * addDTO映射pojo
     */
    BookCatalog fromAddDTO(BookCatalogAddDTO bookCatalogAddDTO);

    /**
     * 将updateDTO的值映射到pojo
     */
    void setUpdateDTO(@MappingTarget BookCatalog bookCatalog, BookCatalogUpdateDTO bookCatalogUpdateDTO);

    /**
     * pojo映射vo
     */
    BookCatalogShowVO toShowVO(BookCatalog bookCatalog);
}
