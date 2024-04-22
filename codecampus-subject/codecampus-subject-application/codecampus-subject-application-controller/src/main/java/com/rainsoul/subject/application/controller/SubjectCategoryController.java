package com.rainsoul.subject.application.controller;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.google.common.base.Preconditions;
import com.rainsoul.subject.application.convert.SubjectCategoryDTOConverter;
import com.rainsoul.subject.application.dto.SubjectCategoryDTO;
import com.rainsoul.subject.common.entity.Result;
import com.rainsoul.subject.domain.entity.SubjectCategoryBO;
import com.rainsoul.subject.domain.service.SubjectCategoryDomainService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/subject/category")
@Slf4j
public class SubjectCategoryController {

    @Resource
    private SubjectCategoryDomainService subjectCategoryDomainService;

    /**
     * 添加主题分类信息
     *
     * @param subjectCategoryDTO 主题分类数据传输对象，包含分类的详细信息
     * @return 返回操作结果，成功返回true，失败返回false，并附带错误信息
     */
    @PostMapping("/add")
    public Result<Boolean> add(@RequestBody SubjectCategoryDTO subjectCategoryDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectCategoryController.add.dto:{}", JSON.toJSONString(subjectCategoryDTO));
            }
            // 校验传入参数的合法性
            Preconditions.checkNotNull(subjectCategoryDTO.getCategoryType(), "分类类型不能为空");
            Preconditions.checkArgument(!StringUtils.isBlank(subjectCategoryDTO.getCategoryName()), "分类名称不能为空");
            Preconditions.checkNotNull(subjectCategoryDTO.getParentId(), "分类父级id不能为空");
            // 转换DTO为业务对象
            SubjectCategoryBO subjectCategoryBO = SubjectCategoryDTOConverter
                    .INSTANCE.convertDtoToCategoryBO(subjectCategoryDTO);
            // 调用领域服务，添加分类
            subjectCategoryDomainService.add(subjectCategoryBO);
            return Result.ok(true);
        } catch (Exception e) {
            // 记录异常信息
            log.error("SubjectCategoryController.add.error:{}", e.getMessage(), e);
            return Result.fail("新增分类失败");
        }
    }

    /**
     * 查询岗位大类
     *
     * @param subjectCategoryDTO 请求参数，包含查询所需的条件信息
     * @return 返回岗位大类的查询结果，封装在Result<List<SubjectCategoryDTO>>中，
     *         如果查询成功，Result的isSuccess方法将返回true，并包含岗位大类列表；
     *         如果查询失败，Result的isSuccess方法将返回false，并提供错误信息。
     */
    @PostMapping("/queryPrimaryCategory")
    public Result<List<SubjectCategoryDTO>> queryPrimaryCategory(@RequestBody SubjectCategoryDTO subjectCategoryDTO) {
        try {
            // 将请求DTO转换为业务对象
            SubjectCategoryBO subjectCategoryBO = SubjectCategoryDTOConverter.INSTANCE.
                    convertDtoToCategoryBO(subjectCategoryDTO);
            // 调用领域服务查询岗位大类
            List<SubjectCategoryBO> subjectCategoryBOList = subjectCategoryDomainService.queryCategory(subjectCategoryBO);
            // 将业务对象列表转换为DTO列表
            List<SubjectCategoryDTO> subjectCategoryDTOList = SubjectCategoryDTOConverter.INSTANCE
                    .convertCategoryBOListToDTOList(subjectCategoryBOList);
            return Result.ok(subjectCategoryDTOList);
        } catch (Exception e) {
            log.error("SubjectCategoryController.queryPrimaryCategory.error:{}", e.getMessage(), e);
            return Result.fail("查询失败");
        }
    }


    /**
     * 根据一级分类的ID查询相应的二级分类信息。
     *
     * @param subjectCategoryDTO 包含一级分类ID的信息对象。
     * @return 返回查询到的二级分类列表的结果对象，其中包含二级分类的详细信息。
     */
    @PostMapping("/queryCategoryByPrimary")
    public Result<List<SubjectCategoryDTO>> queryCategoryByPrimary(@RequestBody SubjectCategoryDTO subjectCategoryDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectCategoryController.queryCategoryByPrimary.dto:{}", JSON.toJSONString(subjectCategoryDTO));
            }
            // 检查传入的一级分类ID不能为空
            Preconditions.checkNotNull(subjectCategoryDTO.getParentId(), "分类id不能为空");
            // 将DTO对象转换为业务对象
            SubjectCategoryBO subjectCategoryBO = SubjectCategoryDTOConverter.INSTANCE.convertDtoToCategoryBO(subjectCategoryDTO);
            // 调用领域服务查询二级分类信息
            List<SubjectCategoryBO> subjectCategoryBOList = subjectCategoryDomainService.queryCategory(subjectCategoryBO);
            // 将业务对象列表转换为DTO列表
            List<SubjectCategoryDTO> subjectCategoryDTOList = SubjectCategoryDTOConverter.INSTANCE.convertCategoryBOListToDTOList(subjectCategoryBOList);
            return Result.ok(subjectCategoryDTOList);
        } catch (Exception e) {
            log.error("SubjectCategoryController.queryCategoryByPrimary.error:{}", e.getMessage(), e);
            return Result.fail("查询失败");
        }
    }



    /**
     * 更新分类
     */
    @PostMapping("/update")
    public Result<Boolean> update(@RequestBody SubjectCategoryDTO subjectCategoryDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectCategoryController.update.dto:{}", JSON.toJSONString(subjectCategoryDTO));
            }
            SubjectCategoryBO subjectCategoryBO = SubjectCategoryDTOConverter.INSTANCE.
                    convertDtoToCategoryBO(subjectCategoryDTO);
            Boolean result = subjectCategoryDomainService.update(subjectCategoryBO);
            return Result.ok(result);
        } catch (Exception e) {
            log.error("SubjectCategoryController.update.error:{}", e.getMessage(), e);
            return Result.fail("更新分类失败");
        }
    }

    /**
     * 删除分类
     */
    @PostMapping("/delete")
    public Result<Boolean> delete(@RequestBody SubjectCategoryDTO subjectCategoryDTO) {
        try {
            if (log.isInfoEnabled()) {
                log.info("SubjectCategoryController.delete.dto:{}", JSON.toJSONString(subjectCategoryDTO));
            }
            SubjectCategoryBO subjectCategoryBO = SubjectCategoryDTOConverter.INSTANCE.
                    convertDtoToCategoryBO(subjectCategoryDTO);
            Boolean result = subjectCategoryDomainService.delete(subjectCategoryBO);
                return Result.ok(result);
        } catch (Exception e) {
            log.error("SubjectCategoryController.delete.error:{}", e.getMessage(), e);
            return Result.fail("删除分类失败");
        }
    }


}
