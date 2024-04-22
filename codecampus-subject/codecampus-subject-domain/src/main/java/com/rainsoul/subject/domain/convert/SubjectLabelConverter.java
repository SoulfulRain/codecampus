package com.rainsoul.subject.domain.convert;

import com.rainsoul.subject.domain.entity.SubjectLabelBO;
import com.rainsoul.subject.infra.basic.entity.SubjectLabel;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SubjectLabelConverter {

    SubjectLabelConverter INSTANCE = Mappers.getMapper(SubjectLabelConverter.class);

    SubjectLabel convertBoToLabel(SubjectLabelBO subjectLabelBO);

    List<SubjectLabelBO> convertLabelToBo(List<SubjectLabel> subjectLabel);
}
