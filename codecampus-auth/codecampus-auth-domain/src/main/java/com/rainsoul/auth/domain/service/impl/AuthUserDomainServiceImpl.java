package com.rainsoul.auth.domain.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.SaTokenInfo;
import com.rainsoul.auth.common.enums.AuthUserStatusEnum;
import com.rainsoul.auth.common.enums.IsDeletedFlagEnum;
import com.rainsoul.auth.domain.converter.AuthUserBOConverter;
import com.rainsoul.auth.domain.entity.AuthUserBO;
import com.rainsoul.auth.domain.service.AuthUserDomainService;
import com.rainsoul.auth.infra.basic.entity.AuthUser;
import com.rainsoul.auth.infra.basic.service.AuthUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AuthUserDomainServiceImpl implements AuthUserDomainService {

    @Resource
    private AuthUserService authUserService;

    private String salt = "anlan";

    @Override
    public Boolean register(AuthUserBO authUserBO) {
        AuthUser authUser = AuthUserBOConverter.INSTANCE.convertBOToEntity(authUserBO);
        authUser.setPassword(SaSecureUtil.md5BySalt(authUser.getPassword(), salt));

        authUser.setStatus(AuthUserStatusEnum.OPEN.getCode());
        authUser.setIsDeleted(IsDeletedFlagEnum.UN_DELETED.getCode());
        Integer count = authUserService.insert(authUser);
        //建立一个初步的角色关联
        //把当前用户的角色和权限都存放到redis里面。
        return count > 0;
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
