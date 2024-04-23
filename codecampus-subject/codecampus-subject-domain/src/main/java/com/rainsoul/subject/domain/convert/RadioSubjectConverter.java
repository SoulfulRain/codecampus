package com.rainsoul.subject.domain.convert;

import com.rainsoul.subject.domain.entity.SubjectAnswerBO;
import com.rainsoul.subject.infra.basic.entity.SubjectRadio;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface RadioSubjectConverter {

    RadioSubjectConverter INSTANCE = Mappers.getMapper(RadioSubjectConverter.class);

    SubjectRadio convertBoToEntity(SubjectAnswerBO subjectAnswerBO);

    List<SubjectAnswerBO> convertEntityToBo(List<SubjectRadio> subjectRadioList);

}
