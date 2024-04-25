package com.rainsoul.auth.infra.basic.service;

import com.rainsoul.auth.infra.basic.entity.AuthUserRole;

/**
 * (AuthUserRole)表服务接口
 *
 * @author makejava
 * @since 2024-04-25 19:03:32
 */
public interface AuthUserRoleService {

    /**
     * 通过ID查询单条数据
     *
     * @param id
     * @return 实例对象
     */
    AuthUserRole queryById(Long id);

    /**
     * 新增数据
     *
     * @param authUserRole 实例对象
     * @return 实例对象
     */
    AuthUserRole insert(AuthUserRole authUserRole);

    /**
     * 修改数据
     *
     * @param authUserRole 实例对象
     * @return 实例对象
     */
    AuthUserRole update(AuthUserRole authUserRole);

    /**
     * 通过主键删除数据
     *
     * @param id
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
