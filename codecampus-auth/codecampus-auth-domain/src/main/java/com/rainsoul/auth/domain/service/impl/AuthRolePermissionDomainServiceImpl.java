package com.rainsoul.auth.domain.service.impl;

import com.rainsoul.auth.common.enums.IsDeletedFlagEnum;
import com.rainsoul.auth.domain.entity.AuthRolePermissionBO;
import com.rainsoul.auth.domain.service.AuthRolePermissionDomainService;
import com.rainsoul.auth.infra.basic.entity.AuthRolePermission;
import com.rainsoul.auth.infra.basic.service.AuthRolePermissionService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@Service
public class AuthRolePermissionDomainServiceImpl implements AuthRolePermissionDomainService {


    @Resource
    private AuthRolePermissionService authRolePermissionService;

    /**
     * 向系统中添加角色权限关联信息。
     *
     * @param authRolePermissionBO 包含角色ID和权限ID列表的对象，用于批量添加角色与权限的关联。
     * @return 返回一个布尔值，如果添加成功则为true，否则为false。
     */
    @Override
    public Boolean add(AuthRolePermissionBO authRolePermissionBO) {
        // 初始化用于存储角色权限关联信息的列表
        List<AuthRolePermission> rolePermissionList = new LinkedList<>();
        Long roleId = authRolePermissionBO.getRoleId();
        // 遍历权限ID列表，为每个权限ID创建一个角色权限关联对象，并加入到列表中
        authRolePermissionBO.getPermissionIdList().forEach(permissionId -> {
            AuthRolePermission authRolePermission = new AuthRolePermission();
            authRolePermission.setRoleId(roleId);
            authRolePermission.setPermissionId(permissionId);
            authRolePermission.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
            rolePermissionList.add(authRolePermission);
        });
        // 批量插入角色权限关联信息到数据库
        int count = authRolePermissionService.batchInsert(rolePermissionList);
        // 根据插入结果判断操作是否成功
        return count > 0;
    }


}
