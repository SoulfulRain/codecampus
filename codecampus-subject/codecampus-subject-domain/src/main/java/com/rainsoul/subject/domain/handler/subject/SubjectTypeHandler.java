package com.rainsoul.subject.domain.handler.subject;

import com.rainsoul.subject.common.enums.SubjectInfoTypeEnum;
import com.rainsoul.subject.domain.entity.SubjectInfoBO;
import com.rainsoul.subject.domain.entity.SubjectOptionBO;

public interface SubjectTypeHandler {

    /**
     * 获取枚举类型
     */
    SubjectInfoTypeEnum getHandlerType();

    /**
     * 添加题目
     */
    void add(SubjectInfoBO subjectInfoBO);

    /**
     * 查询题目
     */
    SubjectOptionBO query(int subjectId);
}
