package com.rainsoul.subject.application.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.google.common.base.Preconditions;
import com.rainsoul.subject.application.convert.SubjectAnswerDTOConverter;
import com.rainsoul.subject.application.convert.SubjectInfoDTOConverter;
import com.rainsoul.subject.application.dto.SubjectInfoDTO;
import com.rainsoul.subject.common.entity.PageResult;
import com.rainsoul.subject.common.entity.Result;
import com.rainsoul.subject.domain.entity.SubjectAnswerBO;
import com.rainsoul.subject.domain.entity.SubjectInfoBO;
import com.rainsoul.subject.domain.service.SubjectInfoDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 刷题controller
 *
 * @author RainSoul
 * @create 2024/4/21
 */
@RestController
@Slf4j
@RequestMapping("/subject")
public class SubjectController {

    @Resource
    private SubjectInfoDomainService subjectInfoDomainService;

    /**
     * 添加题目的接口
     *
     * @param subjectInfoDTO 题目信息数据传输对象，包含题目的详细信息，如名称、类型、分数、分类ID和标签ID等。
     * @return 返回操作结果，成功返回true，失败返回false，并提供错误信息。
     */
    @PostMapping("/add")
    public Result<Boolean> add(@RequestBody SubjectInfoDTO subjectInfoDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectController.add.dto:{}", JSON.toJSONString(subjectInfoDTO));
            }

            Preconditions.checkArgument(!StringUtils.isBlank(subjectInfoDTO.getSubjectName()),
                    "题目名称不能为空");
            Preconditions.checkNotNull(subjectInfoDTO.getSubjectType(),
                    "题目类型不能为空");
            Preconditions.checkNotNull(subjectInfoDTO.getSubjectScore(),
                    "题目分数不能为空");
            Preconditions.checkArgument(!CollectionUtils.isEmpty(subjectInfoDTO.getCategoryIds())
                    , "分类id不能为空");
            Preconditions.checkArgument(!CollectionUtils.isEmpty(subjectInfoDTO.getLabelIds())
                    , "标签id不能为空");

            // 将DTO转换为BO，进行业务逻辑处理使用的对象转换
            SubjectInfoBO subjectInfoBO = SubjectInfoDTOConverter.INSTANCE
                    .convertDTOToBO(subjectInfoDTO);

            // 将选项列表从DTO转换为BO
            List<SubjectAnswerBO> subjectAnswerBOList = SubjectAnswerDTOConverter.INSTANCE
                    .convertListDTOToBO(subjectInfoDTO.getOptionList());

            // 设置题目选项
            subjectInfoBO.setOptionList(subjectAnswerBOList);

            // 调用领域服务，添加题目信息
            subjectInfoDomainService.add(subjectInfoBO);
            return Result.ok(true);
        } catch (Exception e) {
            log.error("SubjectController.add.error:{}", e.getMessage());
            return Result.fail("新增题目失败");
        }
    }

    /**
     * 查询题目列表接口
     *
     * @param subjectInfoDTO 包含查询条件和分页信息的数据传输对象
     * @return 返回题目的分页查询结果，包括总数和题目列表
     */
    @PostMapping("/getSubjectPage")
    public Result<PageResult<SubjectInfoBO>> getSubjectPage(@RequestBody SubjectInfoDTO subjectInfoDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectController.getSubjectPage.dto:{}", JSON.toJSONString(subjectInfoDTO));
            }
            // 检查必要的查询条件是否为空
            Preconditions.checkNotNull(subjectInfoDTO.getCategoryId(), "分类id不能为空");
            Preconditions.checkNotNull(subjectInfoDTO.getLabelId(), "标签id不能为空");
            // 将DTO转换为业务对象
            SubjectInfoBO subjectInfoBO = SubjectInfoDTOConverter.INSTANCE.convertDTOToBO(subjectInfoDTO);
            // 设置分页参数
            subjectInfoBO.setPageNo(subjectInfoDTO.getPageNo());
            subjectInfoBO.setPageSize(subjectInfoDTO.getPageSize());
            // 调用领域服务进行分页查询
            PageResult<SubjectInfoBO> boPageResult = subjectInfoDomainService.getSubjectPage(subjectInfoBO);
            return Result.ok(boPageResult);
        } catch (Exception e) {
            log.error("SubjectCategoryController.add.error:{}", e.getMessage(), e);
            return Result.fail("分页查询题目失败");
        }
    }


    /**
     * 查询题目信息
     *
     * @param subjectInfoDTO 包含题目查询条件的数据传输对象，至少包含题目的ID。
     * @return Result<SubjectInfoDTO> 返回查询结果的包装对象，包含题目信息的DTO。
     */
    @PostMapping("/querySubjectInfo")
    public Result<SubjectInfoDTO> querySubjectInfo(@RequestBody SubjectInfoDTO subjectInfoDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectController.querySubjectInfo.dto:{}", JSON.toJSONString(subjectInfoDTO));
            }
            // 检查题目ID不能为空
            Preconditions.checkNotNull(subjectInfoDTO.getId(), "题目id不能为空");
            // 将DTO转换为业务对象
            SubjectInfoBO subjectInfoBO = SubjectInfoDTOConverter.INSTANCE.convertDTOToBO(subjectInfoDTO);
            // 调用领域服务查询题目信息
            SubjectInfoBO boResult  = subjectInfoDomainService.querySubjectInfo(subjectInfoBO);
            // 将业务对象转换回DTO
            SubjectInfoDTO dto = SubjectInfoDTOConverter.INSTANCE.convertBOToDTO(boResult);
            return Result.ok(dto);
        } catch (Exception e) {
            log.error("SubjectCategoryController.add.error:{}", e.getMessage(), e);
            return Result.fail("查询题目详情失败");
        }
    }




}
