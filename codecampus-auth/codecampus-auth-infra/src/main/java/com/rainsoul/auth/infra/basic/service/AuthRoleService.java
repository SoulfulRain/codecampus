package com.rainsoul.auth.infra.basic.service;

import com.rainsoul.auth.infra.basic.entity.AuthRole;
import com.rainsoul.auth.infra.basic.entity.AuthUser;

import java.util.List;

/**
 * (AuthRole)表服务接口
 *
 * @author makejava
 * @since 2024-04-25 19:01:35
 */
public interface AuthRoleService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    AuthRole queryById(Long id);

    /**
     * 新增数据
     *
     * @param authRole 实例对象
     * @return 实例对象
     */
    Integer insert(AuthRole authRole);

    /**
     * 修改数据
     *
     * @param authRole 实例对象
     * @return 实例对象
     */
    int update(AuthRole authRole);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

    /**
     * 查询指定行数据
     *
     * @param authUser 查询条件
     * @return 对象列表
     */
    List<AuthUser> queryAllByLimit(AuthUser authUser);

    /**
     * 根据条件查询角色
     */
    AuthRole queryByCondition(AuthRole authRole);

}
