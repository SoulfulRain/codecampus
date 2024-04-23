package com.rainsoul.subject.domain.service;

import com.rainsoul.subject.common.entity.PageResult;
import com.rainsoul.subject.domain.entity.SubjectInfoBO;

public interface SubjectInfoDomainService {
    /**
     * 新增题目
     */
    void add(SubjectInfoBO subjectInfoBO);

    /**
     * 分页查询
     */
    PageResult<SubjectInfoBO> getSubjectPage(SubjectInfoBO subjectInfoBO);

    SubjectInfoBO querySubjectInfo(SubjectInfoBO subjectInfoBO);
}
