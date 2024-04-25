package com.rainsoul.auth.infra.basic.service;

import com.rainsoul.auth.infra.basic.entity.AuthPermission;

/**
 * (AuthPermission)表服务接口
 *
 * @author makejava
 * @since 2024-04-25 19:02:16
 */
public interface AuthPermissionService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    AuthPermission queryById(Long id);

    /**
     * 新增数据
     *
     * @param authPermission 实例对象
     * @return 实例对象
     */
    AuthPermission insert(AuthPermission authPermission);

    /**
     * 修改数据
     *
     * @param authPermission 实例对象
     * @return 实例对象
     */
    AuthPermission update(AuthPermission authPermission);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
