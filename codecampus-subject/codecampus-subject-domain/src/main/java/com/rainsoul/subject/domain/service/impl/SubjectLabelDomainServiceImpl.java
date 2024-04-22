package com.rainsoul.subject.domain.service.impl;

import com.alibaba.fastjson.JSON;
import com.rainsoul.subject.common.enums.CategoryTypeEnum;
import com.rainsoul.subject.common.enums.IsDeletedFlagEnum;
import com.rainsoul.subject.domain.convert.SubjectLabelConverter;
import com.rainsoul.subject.domain.entity.SubjectLabelBO;
import com.rainsoul.subject.domain.service.SubjectLabelDomainService;
import com.rainsoul.subject.infra.basic.entity.SubjectLabel;
import com.rainsoul.subject.infra.basic.entity.SubjectMapping;
import com.rainsoul.subject.infra.basic.service.SubjectLabelService;
import com.rainsoul.subject.infra.basic.service.SubjectMappingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SubjectLabelDomainServiceImpl implements SubjectLabelDomainService {

    @Resource
    private SubjectLabelService subjectLabelService;

    @Resource
    private SubjectMappingService subjectMappingService;

    @Override
    public Boolean add(SubjectLabelBO subjectLabelBO) {
        if (log.isInfoEnabled()) {
            log.info("SubjectLabelDomainServiceImpl.add.bo:{}", JSON.toJSONString(subjectLabelBO));
        }
        SubjectLabel subjectLabel = SubjectLabelConverter.INSTANCE
                .convertBoToLabel(subjectLabelBO);
        subjectLabel.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        int count =  subjectLabelService.insert(subjectLabel);
        return count > 0;
    }

    @Override
    public Boolean update(SubjectLabelBO subjectLabelBO) {
        if (log.isInfoEnabled()) {
            log.info("SubjectLabelDomainServiceImpl.update.bo:{}", JSON.toJSONString(subjectLabelBO));
        }
        SubjectLabel subjectLabel = SubjectLabelConverter.INSTANCE.convertBoToLabel(subjectLabelBO);
        subjectLabel.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        int count =  subjectLabelService.update(subjectLabel);
        return count > 0;
    }

    @Override
    public Boolean delete(SubjectLabelBO subjectLabelBO) {
        if (log.isInfoEnabled()) {
            log.info("SubjectLabelDomainServiceImpl.update.bo:{}", JSON.toJSONString(subjectLabelBO));
        }
        SubjectLabel subjectLabel = SubjectLabelConverter.INSTANCE
                .convertBoToLabel(subjectLabelBO);
        subjectLabel.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        int count = subjectLabelService.update(subjectLabel);
        return count > 0;
    }

    @Override
    public List<SubjectLabelBO> queryLabelByCategoryId(SubjectLabelBO subjectLabelBO) {
        //如果当前分类是1级分类，则查询所有标签
        if (CategoryTypeEnum.PRIMARY.getCode() == subjectLabelBO.getCategoryId()) {
            SubjectLabel subjectLabel = new SubjectLabel();
            subjectLabel.setCategoryId(subjectLabelBO.getCategoryId());
            List<SubjectLabel> subjectLabelList = subjectLabelService.queryByCondition(subjectLabel);
            List<SubjectLabelBO> subjectLabelBOList = SubjectLabelConverter.INSTANCE.convertLabelToBo(subjectLabelList);
            return subjectLabelBOList;
        }
        //如果当前分类是2级分类，则查询当前分类下的所有标签
        Long categoryId = subjectLabelBO.getCategoryId();
        SubjectMapping subjectMapping = new SubjectMapping();
        subjectMapping.setCategoryId(categoryId);
        subjectMapping.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        // 执行关联标签ID的查询
        List<SubjectMapping> mappingList = subjectMappingService.queryLabelId(subjectMapping);
        // 如果没有查询到关联标签，则直接返回空列表
        if (CollectionUtils.isEmpty(mappingList)) {
            return Collections.emptyList();
        }
        // 提取标签ID列表并执行批量查询
        List<Long> labelIdList = mappingList.stream().map(SubjectMapping::getLabelId).collect(Collectors.toList());
        List<SubjectLabel> labelList = subjectLabelService.batchQueryById(labelIdList);
        // 构建并返回业务对象列表
        List<SubjectLabelBO> subjectLabelBOList = new LinkedList<>();
        labelList.forEach(label -> {
            SubjectLabelBO bo = new SubjectLabelBO();
            bo.setId(label.getId());
            bo.setLabelName(label.getLabelName());
            bo.setCategoryId(categoryId);
            bo.setSortNum(label.getSortNum());
            subjectLabelBOList.add(bo);
        });
        return subjectLabelBOList;
    }
}
