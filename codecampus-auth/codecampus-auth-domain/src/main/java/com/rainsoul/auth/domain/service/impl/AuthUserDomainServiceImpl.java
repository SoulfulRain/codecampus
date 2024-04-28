package com.rainsoul.auth.domain.service.impl;

import cn.dev33.satoken.secure.SaSecureUtil;
import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.rainsoul.auth.common.enums.AuthUserStatusEnum;
import com.rainsoul.auth.common.enums.IsDeletedFlagEnum;
import com.rainsoul.auth.domain.constants.AuthConstant;
import com.rainsoul.auth.domain.converter.AuthUserBOConverter;
import com.rainsoul.auth.domain.entity.AuthUserBO;
import com.rainsoul.auth.domain.redis.RedisUtil;
import com.rainsoul.auth.domain.service.AuthUserDomainService;
import com.rainsoul.auth.infra.basic.entity.AuthRole;
import com.rainsoul.auth.infra.basic.entity.AuthUser;
import com.rainsoul.auth.infra.basic.entity.AuthUserRole;
import com.rainsoul.auth.infra.basic.service.*;
import lombok.SneakyThrows;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

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

    @Resource
    private RedisUtil redisUtil;

    private String authPermissionPrefix = "auth.permission";

    private String authRolePrefix = "auth.role";

    private static final String LOGIN_PREFIX = "loginCode";

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
        AuthUser authUser = AuthUserBOConverter.INSTANCE.convertBOToEntity(authUserBO);
        Integer count = authUserService.updateByUserName(authUser);
        return count > 0;
    }

    @Override
    public Boolean delete(AuthUserBO authUserBO) {
        AuthUser authUser = new AuthUser();
        authUser.setId(authUserBO.getId());
        authUser.setIsDeleted(IsDeletedFlagEnum.DELETED.getCode());
        Integer count = authUserService.update(authUser);
        //有任何的更新，都要与缓存进行同步的修改
        return count > 0;
    }

    /**
     * 执行登录操作。
     * 首先通过验证码验证用户身份，然后注册用户，最后登录并返回token信息。
     *
     * @param validCode 验证码，用于验证用户身份。
     * @return 返回登录后的token信息，如果登录失败或验证失败则返回null。
     */
    @Override
    public SaTokenInfo doLogin(String validCode) {
        // 生成登录key，基于验证码
        String loginKey = redisUtil.buildKey(LOGIN_PREFIX, validCode);
        // 根据登录key从Redis获取openId
        String openId = redisUtil.get(loginKey);
        // 如果openId为空，表示验证失败，返回null
        if (StringUtils.isBlank(openId)) {
            return null;
        }
        // 准备用户信息并设置用户名为openId
        AuthUserBO authUserBO = new AuthUserBO();
        authUserBO.setUserName(openId);
        // 注册用户
        this.register(authUserBO);
        // 执行登录操作
        StpUtil.login(openId);
        // 获取登录后的token信息
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        return tokenInfo;
    }


    /**
     * 根据传入的AuthUserBO对象获取对应的用户信息。
     *
     * @param authUserBO 包含用户名称的AuthUserBO对象。
     * @return 返回一个AuthUserBO对象，包含查询到的用户信息。如果没有查询到用户，则返回一个空的AuthUserBO对象。
     */
    @Override
    public AuthUserBO getUserInfo(AuthUserBO authUserBO) {
        // 创建一个AuthUser实例并设置用户名，用于查询条件
        AuthUser authUser = new AuthUser();
        authUser.setUserName(authUserBO.getUserName());

        // 根据用户名查询用户列表
        List<AuthUser> userList = authUserService.queryByCondition(authUser);

        // 如果查询结果为空，返回一个新的空AuthUserBO对象
        if (CollectionUtils.isEmpty(userList)) {
            return new AuthUserBO();
        }

        // 从查询结果中获取第一个用户，然后将其转换为AuthUserBO对象后返回
        AuthUser user = userList.get(0);
        return AuthUserBOConverter.INSTANCE.convertEntityToBO(user);
    }
}
