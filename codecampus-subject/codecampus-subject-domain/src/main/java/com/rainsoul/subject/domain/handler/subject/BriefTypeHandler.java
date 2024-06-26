package com.rainsoul.subject.domain.handler.subject;

import com.rainsoul.subject.common.enums.IsDeletedFlagEnum;
import com.rainsoul.subject.common.enums.SubjectInfoTypeEnum;
import com.rainsoul.subject.domain.convert.BriefSubjectConverter;
import com.rainsoul.subject.domain.entity.SubjectInfoBO;
import com.rainsoul.subject.domain.entity.SubjectOptionBO;
import com.rainsoul.subject.infra.basic.entity.SubjectBrief;
import com.rainsoul.subject.infra.basic.service.SubjectBriefService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class BriefTypeHandler implements SubjectTypeHandler{

    @Resource
    private SubjectBriefService subjectBriefService;
    
    @Override
    public SubjectInfoTypeEnum getHandlerType() {
        return SubjectInfoTypeEnum.BRIEF;
    }

    @Override
    public void add(SubjectInfoBO subjectInfoBO) {
        SubjectBrief subjectBrief = BriefSubjectConverter.INSTANCE.convertBoToEntity(subjectInfoBO);
        subjectBrief.setSubjectId(subjectInfoBO.getId().intValue());
        subjectBrief.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        subjectBriefService.insert(subjectBrief);
    }

    @Override
    public SubjectOptionBO query(int subjectId) {
        SubjectBrief subjectBrief = new SubjectBrief();
        subjectBrief.setSubjectId(subjectId);
        SubjectBrief result = subjectBriefService.queryByCondition(subjectBrief);
        SubjectOptionBO subjectOptionBO = new SubjectOptionBO();
        subjectOptionBO.setSubjectAnswer(result.getSubjectAnswer());
        return subjectOptionBO;
    }
}
