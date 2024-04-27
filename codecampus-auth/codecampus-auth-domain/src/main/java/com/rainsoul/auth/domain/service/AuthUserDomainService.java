package com.rainsoul.auth.domain.service;

import cn.dev33.satoken.stp.SaTokenInfo;
import com.rainsoul.auth.domain.entity.AuthUserBO;

public interface AuthUserDomainService {
    /**
     * 注册
     */
    Boolean register(AuthUserBO authUserBO);

    /**
     * 更新用户信息
     */
    Boolean update(AuthUserBO authUserBO);

    /**
     * 更新用户信息
     */
    Boolean delete(AuthUserBO authUserBO);

    SaTokenInfo doLogin(String validCode);

    AuthUserBO getUserInfo(AuthUserBO authUserBO);

}
