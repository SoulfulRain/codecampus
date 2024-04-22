package com.rainsoul.subject.infra.basic.service.impl;

import com.rainsoul.subject.infra.basic.entity.SubjectMapping;
import com.rainsoul.subject.infra.basic.mapper.SubjectMappingDao;
import com.rainsoul.subject.infra.basic.service.SubjectMappingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 题目分类关系表(SubjectMapping)表服务实现类
 *
 * @author makejava
 * @since 2024-04-22 19:59:56
 */
@Service("subjectMappingService")
public class SubjectMappingServiceImpl implements SubjectMappingService {
    @Resource
    private SubjectMappingDao subjectMappingDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public SubjectMapping queryById(Long id) {
        return this.subjectMappingDao.queryById(id);
    }

    /**
     * 新增数据
     *
     * @param subjectMapping 实例对象
     * @return 实例对象
     */
    @Override
    public SubjectMapping insert(SubjectMapping subjectMapping) {
        this.subjectMappingDao.insert(subjectMapping);
        return subjectMapping;
    }

    /**
     * 修改数据
     *
     * @param subjectMapping 实例对象
     * @return 实例对象
     */
    @Override
    public int update(SubjectMapping subjectMapping) {
        return this.subjectMappingDao.update(subjectMapping);
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.subjectMappingDao.deleteById(id) > 0;
    }
}
