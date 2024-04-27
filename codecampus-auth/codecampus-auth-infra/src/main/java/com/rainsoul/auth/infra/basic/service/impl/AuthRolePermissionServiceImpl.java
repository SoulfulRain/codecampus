package com.rainsoul.auth.infra.basic.service.impl;

import com.rainsoul.auth.infra.basic.mapper.AuthRolePermissionDao;
import com.rainsoul.auth.infra.basic.entity.AuthRolePermission;
import com.rainsoul.auth.infra.basic.service.AuthRolePermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * (AuthRolePremission)表服务实现类
 *
 * @author makejava
 * @since 2024-04-25 19:02:51
 */
@Service("authRolePremissionService")
public class AuthRolePermissionServiceImpl implements AuthRolePermissionService {
    @Resource
    private AuthRolePermissionDao authRolePermissionDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public AuthRolePermission queryById(Long id) {
        return this.authRolePermissionDao.queryById(id);
    }

    /**
     * 新增数据
     *
     * @param authRolePremission 实例对象
     * @return 实例对象
     */
    @Override
    public AuthRolePermission insert(AuthRolePermission authRolePremission) {
        this.authRolePermissionDao.insert(authRolePremission);
        return authRolePremission;
    }

    /**
     * 修改数据
     *
     * @param authRolePremission 实例对象
     * @return 实例对象
     */
    @Override
    public AuthRolePermission update(AuthRolePermission authRolePremission) {
        this.authRolePermissionDao.update(authRolePremission);
        return this.queryById(authRolePremission.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.authRolePermissionDao.deleteById(id) > 0;
    }

    @Override
    public List<AuthRolePermission> queryByCondition(AuthRolePermission authRolePermission) {
        return this.authRolePermissionDao.queryAllByLimit(authRolePermission);
    }
}
