package com.rainsoul.subject.application.convert;

import com.rainsoul.subject.application.dto.SubjectCategoryDTO;
import com.rainsoul.subject.domain.entity.SubjectCategoryBO;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SubjectCategoryDTOConverter {
    SubjectCategoryDTOConverter INSTANCE = Mappers.getMapper(SubjectCategoryDTOConverter.class);

    SubjectCategoryBO convertDtoToCategoryBO(SubjectCategoryDTO subjectCategoryDTO);

    List<SubjectCategoryDTO> convertCategoryBOListToDTOList(List<SubjectCategoryBO> subjectCategoryBOList);

}
