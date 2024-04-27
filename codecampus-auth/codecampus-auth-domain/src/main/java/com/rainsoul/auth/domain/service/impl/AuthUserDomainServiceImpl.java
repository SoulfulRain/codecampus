package com.rainsoul.auth.domain.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.SaTokenInfo;
import com.rainsoul.auth.common.enums.AuthUserStatusEnum;
import com.rainsoul.auth.common.enums.IsDeletedFlagEnum;
import com.rainsoul.auth.domain.constants.AuthConstant;
import com.rainsoul.auth.domain.converter.AuthUserBOConverter;
import com.rainsoul.auth.domain.entity.AuthUserBO;
import com.rainsoul.auth.domain.service.AuthUserDomainService;
import com.rainsoul.auth.infra.basic.entity.AuthRole;
import com.rainsoul.auth.infra.basic.entity.AuthUser;
import com.rainsoul.auth.infra.basic.entity.AuthUserRole;
import com.rainsoul.auth.infra.basic.service.*;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

@Service
public class AuthUserDomainServiceImpl implements AuthUserDomainService {

    @Resource
    private AuthUserService authUserService;

    @Resource
    private AuthRoleService authRoleService;

    @Resource
    private AuthPermissionService authPermissionService;

    @Resource
    private AuthUserRoleService authUserRoleService;

    @Resource
    private AuthRolePermissionService authRolePermissionService;

    private String salt = "anlan";

    @Override
    @SneakyThrows
    @Transactional(rollbackFor = Exception.class)
    public Boolean register(AuthUserBO authUserBO) {
        //检验用户是否存在
        AuthUser authUserCheck = new AuthUser();
        authUserCheck.setUserName(authUserBO.getUserName());
        List<AuthUser> authUserList = authUserService.queryByCondition(authUserCheck);
        if (authUserList.size() > 0) {
            return true;
        }

        AuthUser authUser = AuthUserBOConverter.INSTANCE.convertBOToEntity(authUserBO);
        if (StringUtils.isNotBlank(authUser.getPassword())) {
            authUser.setPassword(SaSecureUtil.md5BySalt(authUser.getPassword(), salt));
        }
        if (StringUtils.isBlank(authUser.getAvatar())) {
            authUser.setAvatar("![](https://obsidian-java-1323045770.cos.ap-chengdu.myqcloud.com/OOP/20240427212119.png)");
        }
        authUser.setStatus(AuthUserStatusEnum.OPEN.getCode());
        authUser.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        Integer count = authUserService.insert(authUser);

        //建立一个初步的角色关联
        AuthRole authRole = new AuthRole();
        authRole.setRoleKey(AuthConstant.NORMAL_USER);
        AuthRole authRoleResult = authRoleService.queryByCondition(authRole);
        Long authRoleResultId = authRoleResult.getId();
        Long authUserId = authUser.getId();

        AuthUserRole authUserRole = new AuthUserRole();
        authUserRole.setRoleId(authRoleResultId);
        authUserRole.setUserId(authUserId);
        authUserRole.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        authUserRoleService.insert(authUserRole);

     /*   AuthRolePermission authRolePermission = new AuthRolePermission();
        authRolePermission.setRoleId(authRoleResultId);
        List<AuthRolePermission> rolePermissionList = authRolePermissionService
                .queryByCondition(authRolePermission);*/

        return count > 0;

        //把当前用户的角色和权限都存放到redis里面。
    }

    @Override
    public Boolean update(AuthUserBO authUserBO) {
        return null;
    }

    @Override
    public Boolean delete(AuthUserBO authUserBO) {
        return null;
    }

    @Override
    public SaTokenInfo doLogin(String validCode) {
        return null;
    }

    @Override
    public AuthUserBO getUserInfo(AuthUserBO authUserBO) {
        return null;
    }
}
