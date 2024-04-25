package com.rainsoul.auth.infra.basic.service;

import com.rainsoul.auth.infra.basic.entity.AuthUser;

/**
 * (AuthUser)表服务接口
 *
 * @author makejava
 * @since 2024-04-25 19:03:12
 */
public interface AuthUserService {

    /**
     * 通过ID查询单条数据
     *
     * @param  id
     * @return 实例对象
     */
    AuthUser queryById(Long id);

    /**
     * 新增数据
     *
     * @param authUser 实例对象
     * @return 实例对象
     */
    AuthUser insert(AuthUser authUser);

    /**
     * 修改数据
     *
     * @param authUser 实例对象
     * @return 实例对象
     */
    AuthUser update(AuthUser authUser);

    /**
     * 通过主键删除数据
     *
     * @param  id
     * @return 是否成功
     */
    boolean deleteById(Long id);

}
