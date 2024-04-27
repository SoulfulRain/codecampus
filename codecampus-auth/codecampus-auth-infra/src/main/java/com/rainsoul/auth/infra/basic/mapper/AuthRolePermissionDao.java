package com.rainsoul.auth.infra.basic.mapper;

import com.rainsoul.auth.infra.basic.entity.AuthRolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * (AuthRolePremission)表数据库访问层
 *
 * @author makejava
 * @since 2024-04-25 19:02:51
 */
public interface AuthRolePermissionDao {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    AuthRolePermission queryById(Long id);

    /**
     * 查询指定行数据
     *
     * @param authRolePermission 查询条件
     * @return 对象列表
     */
    List<AuthRolePermission> queryAllByLimit(AuthRolePermission authRolePermission);

    /**
     * 统计总行数
     *
     * @param authRolePremission 查询条件
     * @return 总行数
     */
    long count(AuthRolePermission authRolePremission);

    /**
     * 新增数据
     *
     * @param authRolePremission 实例对象
     * @return 影响行数
     */
    int insert(AuthRolePermission authRolePremission);

    /**
     * 批量新增数据（MyBatis原生foreach方法）
     *
     * @param entities List<AuthRolePremission> 实例对象列表
     * @return 影响行数
     */
    int insertBatch(@Param("entities") List<AuthRolePermission> entities);

    /**
     * 批量新增或按主键更新数据（MyBatis原生foreach方法）
     *
     * @param entities List<AuthRolePremission> 实例对象列表
     * @return 影响行数
     * @throws org.springframework.jdbc.BadSqlGrammarException 入参是空List的时候会抛SQL语句错误的异常，请自行校验入参
     */
    int insertOrUpdateBatch(@Param("entities") List<AuthRolePermission> entities);

    /**
     * 修改数据
     *
     * @param authRolePremission 实例对象
     * @return 影响行数
     */
    int update(AuthRolePermission authRolePremission);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 影响行数
     */
    int deleteById(Long id);

}

