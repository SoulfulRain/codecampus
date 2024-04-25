package com.rainsoul.auth.infra.basic.service.impl;

import com.rainsoul.auth.infra.basic.dao.AuthRoleDao;
import com.rainsoul.auth.infra.basic.entity.AuthRole;
import com.rainsoul.auth.infra.basic.service.AuthRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * (AuthRole)表服务实现类
 *
 * @author makejava
 * @since 2024-04-25 19:01:35
 */
@Service("authRoleService")
public class AuthRoleServiceImpl implements AuthRoleService {
    @Resource
    private AuthRoleDao authRoleDao;

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    @Override
    public AuthRole queryById(Long id) {
        return this.authRoleDao.queryById(id);
    }

    /**
     * 新增数据
     *
     * @param authRole 实例对象
     * @return 实例对象
     */
    @Override
    public AuthRole insert(AuthRole authRole) {
        this.authRoleDao.insert(authRole);
        return authRole;
    }

    /**
     * 修改数据
     *
     * @param authRole 实例对象
     * @return 实例对象
     */
    @Override
    public AuthRole update(AuthRole authRole) {
        this.authRoleDao.update(authRole);
        return this.queryById(authRole.getId());
    }

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    @Override
    public boolean deleteById(Long id) {
        return this.authRoleDao.deleteById(id) > 0;
    }
}
