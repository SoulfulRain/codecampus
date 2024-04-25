package com.rainsoul.auth.infra.basic.service;

import com.rainsoul.auth.infra.basic.entity.AuthRolePermission;

/**
 * (AuthRolePremission)表服务接口
 *
 * @author makejava
 * @since 2024-04-25 19:02:51
 */
public interface AuthRolePermissionService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    AuthRolePermission queryById(Long id);

    /**
     * 新增数据
     *
     * @param authRolePremission 实例对象
     * @return 实例对象
     */
    AuthRolePermission insert(AuthRolePermission authRolePremission);

    /**
     * 修改数据
     *
     * @param authRolePremission 实例对象
     * @return 实例对象
     */
    AuthRolePermission update(AuthRolePermission authRolePremission);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
