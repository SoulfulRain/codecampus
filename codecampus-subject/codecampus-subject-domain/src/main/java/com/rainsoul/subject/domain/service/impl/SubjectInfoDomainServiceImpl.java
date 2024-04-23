package com.rainsoul.subject.domain.service.impl;

import com.alibaba.fastjson.JSON;
import com.rainsoul.subject.common.entity.PageResult;
import com.rainsoul.subject.common.enums.IsDeletedFlagEnum;
import com.rainsoul.subject.domain.convert.SubjectInfoConverter;
import com.rainsoul.subject.domain.entity.SubjectInfoBO;
import com.rainsoul.subject.domain.entity.SubjectOptionBO;
import com.rainsoul.subject.domain.handler.subject.SubjectTypeHandler;
import com.rainsoul.subject.domain.handler.subject.SubjectTypeHandlerFactory;
import com.rainsoul.subject.domain.service.SubjectInfoDomainService;
import com.rainsoul.subject.infra.basic.entity.SubjectInfo;
import com.rainsoul.subject.infra.basic.entity.SubjectLabel;
import com.rainsoul.subject.infra.basic.entity.SubjectMapping;
import com.rainsoul.subject.infra.basic.service.SubjectInfoService;
import com.rainsoul.subject.infra.basic.service.SubjectLabelService;
import com.rainsoul.subject.infra.basic.service.SubjectMappingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SubjectInfoDomainServiceImpl implements SubjectInfoDomainService {

    @Resource
    private SubjectInfoService subjectInfoService;

    @Resource
    private SubjectTypeHandlerFactory subjectTypeHandlerFactory;

    @Resource
    private SubjectMappingService subjectMappingService;

    @Resource
    private SubjectLabelService subjectLabelService;


    /**
     * 添加主题信息及其相关映射关系。
     * @param subjectInfoBO 主题信息业务对象，包含主题的详细信息及分类和标签ID列表。
     * 方法首先将主题信息业务对象转换为主题信息数据对象，设置未删除标志，并插入到数据库中。
     * 然后，根据主题类型获取相应的主题处理器，使用处理器对业务对象进行额外的处理。
     * 最后，根据提供的分类和标签ID列表，创建主题映射对象并批量插入到数据库中。
     */
    @Override
    public void add(SubjectInfoBO subjectInfoBO) {
        if (log.isInfoEnabled()) {
            log.info("SubjectInfoDomainServiceImpl.add.bo:{}", JSON.toJSONString(subjectInfoBO));
        }
        // 将业务对象转换为数据对象并设置删除状态
        SubjectInfo subjectInfo = SubjectInfoConverter.INSTANCE
                .convertBoToInfo(subjectInfoBO);
        subjectInfo.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        // 插入主题信息到数据库
        subjectInfoService.insert(subjectInfo);

        // 获取并使用主题类型处理器对主题信息进行额外处理
        SubjectTypeHandler handler = subjectTypeHandlerFactory.getHandler(subjectInfo.getSubjectType());
        subjectInfoBO.setId(subjectInfo.getId());
        handler.add(subjectInfoBO);

        // 构建主题与分类、标签的映射关系列表，并批量插入到数据库
        List<Integer> categoryIds = subjectInfoBO.getCategoryIds();
        List<Integer> labelIds = subjectInfoBO.getLabelIds();
        List<SubjectMapping> subjectMappingList = new LinkedList<>();
        categoryIds.forEach(categoryId -> {
            labelIds.forEach(labelId -> {
                // 对每个分类和标签组合创建一个主题映射对象
                SubjectMapping subjectMapping = new SubjectMapping();
                subjectMapping.setSubjectId(subjectInfo.getId());
                subjectMapping.setCategoryId(Long.valueOf(categoryId));
                subjectMapping.setLabelId(Long.valueOf(labelId));
                subjectMapping.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
                subjectMappingList.add(subjectMapping);
            });
        });
        // 批量插入主题映射关系到数据库
        subjectMappingService.batchInsert(subjectMappingList);
    }

    /**
     * 获取主题分页信息
     *
     * @param subjectInfoBO 包含分页参数和查询条件的主题信息业务对象
     * @return 返回主题信息的分页结果，包括分页记录和总数
     */
    @Override
    public PageResult<SubjectInfoBO> getSubjectPage(SubjectInfoBO subjectInfoBO) {
        PageResult<SubjectInfoBO> pageResult = new PageResult<>();
        // 设置分页参数
        pageResult.setPageNo(subjectInfoBO.getPageNo());
        pageResult.setPageSize(subjectInfoBO.getPageSize());
        // 计算查询起始位置
        int start = (subjectInfoBO.getPageNo() - 1) * subjectInfoBO.getPageSize();
        // 将主题信息业务对象转换为服务层需要的对象
        SubjectInfo subjectInfo = SubjectInfoConverter.INSTANCE.convertBoToInfo(subjectInfoBO);
        // 根据条件查询记录总数
        int count = subjectInfoService.countByCondition(subjectInfo, subjectInfoBO.getCategoryId()
                , subjectInfoBO.getLabelId());
        // 如果总数为0，直接返回空的分页结果
        if (count == 0) {
            return pageResult;
        }
        // 查询主题信息分页数据
        List<SubjectInfo> subjectInfoList = subjectInfoService.queryPage(subjectInfo, subjectInfoBO.getCategoryId()
                , subjectInfoBO.getLabelId(), start, subjectInfoBO.getPageSize());
        // 将查询到的主题信息转换回业务对象
        List<SubjectInfoBO> subjectInfoBOS = SubjectInfoConverter.INSTANCE.convertListInfoToBO(subjectInfoList);
        // 遍历转换后的主题信息，查询并设置其标签名称
        subjectInfoBOS.forEach(info -> {
            SubjectMapping subjectMapping = new SubjectMapping();
            subjectMapping.setSubjectId(info.getId());
            // 根据主题ID查询关联的标签ID列表
            List<SubjectMapping> mappingList = subjectMappingService.queryLabelId(subjectMapping);
            // 将标签ID列表转换为标签名称列表
            List<Long> labelIds = mappingList.stream().map(SubjectMapping::getLabelId).collect(Collectors.toList());
            List<SubjectLabel> labelList = subjectLabelService.batchQueryById(labelIds);
            List<String> labelNames = labelList.stream().map(SubjectLabel::getLabelName).collect(Collectors.toList());
            // 设置主题信息中的标签名称列表
            info.setLabelName(labelNames);
        });
        // 设置分页结果的记录列表和总数
        pageResult.setRecords(subjectInfoBOS);
        pageResult.setTotal(count);
        return pageResult;
    }

    @Override
    public SubjectInfoBO querySubjectInfo(SubjectInfoBO subjectInfoBO) {
        SubjectInfo subjectInfo = subjectInfoService.queryById(subjectInfoBO.getId());
        SubjectTypeHandler handler = subjectTypeHandlerFactory.getHandler(subjectInfo.getSubjectType());
        SubjectOptionBO optionBO = handler.query(subjectInfo.getId().intValue());
        SubjectInfoBO bo = SubjectInfoConverter.INSTANCE.convertOptionAndInfoToBo(optionBO, subjectInfo);
        SubjectMapping subjectMapping = new SubjectMapping();
        subjectMapping.setSubjectId(subjectInfo.getId());
        subjectMapping.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        List<SubjectMapping> mappingList = subjectMappingService.queryLabelId(subjectMapping);
        List<Long> labelIdList = mappingList.stream().map(SubjectMapping::getLabelId).collect(Collectors.toList());
        List<SubjectLabel> labelList = subjectLabelService.batchQueryById(labelIdList);
        List<String> labelNameList = labelList.stream().map(SubjectLabel::getLabelName).collect(Collectors.toList());
        return bo;
    }


}
