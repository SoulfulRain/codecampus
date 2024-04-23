package com.rainsoul.subject.domain.handler.subject;

import com.rainsoul.subject.common.enums.IsDeletedFlagEnum;
import com.rainsoul.subject.common.enums.SubjectInfoTypeEnum;
import com.rainsoul.subject.domain.convert.MultipleSubjectConverter;
import com.rainsoul.subject.domain.entity.SubjectAnswerBO;
import com.rainsoul.subject.domain.entity.SubjectInfoBO;
import com.rainsoul.subject.domain.entity.SubjectOptionBO;
import com.rainsoul.subject.infra.basic.entity.SubjectMultiple;
import com.rainsoul.subject.infra.basic.service.SubjectMultipleService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@Component
public class MultipleTypeHandler implements SubjectTypeHandler{
    @Resource
    private SubjectMultipleService subjectMultipleService;

    @Override
    public SubjectInfoTypeEnum getHandlerType() {
        return SubjectInfoTypeEnum.MULTIPLE;
    }

    @Override
    public void add(SubjectInfoBO subjectInfoBO) {
        List<SubjectMultiple> subjectMultipleList = new LinkedList<>();
        subjectInfoBO.getOptionList().forEach(option -> {
            SubjectMultiple subjectMultiple = MultipleSubjectConverter.INSTANCE
                    .convertBoToEntity(option);
            subjectMultiple.setSubjectId(subjectInfoBO.getId());
            subjectMultiple.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
            subjectMultipleList.add(subjectMultiple);
        });
        subjectMultipleService.batchInsert(subjectMultipleList);
    }

    @Override
    public SubjectOptionBO query(int subjectId) {
        SubjectMultiple subjectMultiple = new SubjectMultiple();
        subjectMultiple.setSubjectId(Long.valueOf(subjectId));
        List<SubjectMultiple> subjectMultipleList = subjectMultipleService.queryByCondition(subjectMultiple);
        List<SubjectAnswerBO> subjectAnswerBOList = MultipleSubjectConverter.INSTANCE.convertEntityToBoList(subjectMultipleList);
        SubjectOptionBO subjectOptionBO = new SubjectOptionBO();
        subjectOptionBO.setOptionList(subjectAnswerBOList);
        return subjectOptionBO;
    }


}
