package com.rainsoul.subject.domain.convert;

import com.rainsoul.subject.domain.entity.SubjectCategoryBO;
import com.rainsoul.subject.infra.basic.entity.SubjectCategory;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SubjectCategoryConverter {
    SubjectCategoryConverter INSTANCE = Mappers.getMapper(SubjectCategoryConverter.class);

    SubjectCategory convertBoToCategory(SubjectCategoryBO subjectCategoryBO);

    List<SubjectCategoryBO> convertCategoryListToBoList(List<SubjectCategory> subjectCategoryList);


}
