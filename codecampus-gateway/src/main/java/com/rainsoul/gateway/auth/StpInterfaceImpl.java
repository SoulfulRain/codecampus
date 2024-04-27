package com.rainsoul.gateway.auth;

import cn.dev33.satoken.stp.StpInterface;
import com.alibaba.cloud.commons.lang.StringUtils;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.rainsoul.gateway.entity.AuthPermission;
import com.rainsoul.gateway.entity.AuthRole;
import com.rainsoul.gateway.redis.RedisUtil;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义权限验证接口扩展
 */
@Component
public class StpInterfaceImpl implements StpInterface {

    @Resource
    private RedisUtil redisUtil;

    private String authPermissionPrefix = "auth.permission";

    private String authRolePrefix = "auth.role";

    @Override
    public List<String> getPermissionList(Object loginId, String loginType) {
        return getAuth(loginId.toString(), authPermissionPrefix);
    }

    @Override
    public List<String> getRoleList(Object loginId, String loginType) {
        return getAuth(loginId.toString(), authRolePrefix);
    }

    /**
     * 根据登录ID和前缀获取授权信息列表。
     * <p>
     * 此方法会根据提供的登录ID和前缀从Redis中获取授权信息。如果前缀匹配角色授权前缀，则返回角色键的列表；
     * 如果前缀匹配权限授权前缀，则返回权限键的列表。如果在Redis中未找到相应的授权信息，则返回空列表。
     *
     * @param loginId 登录ID，用于构建Redis键。
     * @param prefix  授权信息的前缀，用于区分角色和权限信息。
     * @return 返回一个包含授权键的列表。如果没有找到相应的授权信息，则返回空列表。
     */
    private List<String> getAuth(String loginId, String prefix) {
        // 构建Redis键，并尝试从Redis获取对应的授权值
        String authKey = redisUtil.buildKey(prefix, loginId.toString());
        String authValue = redisUtil.get(authKey);

        // 如果授权值为空，则直接返回空列表
        if (StringUtils.isBlank(authValue)) {
            return Collections.emptyList();
        }

        List<String> authList = new LinkedList<>();

        // 根据前缀分别处理角色和权限的授权信息，将其转换为键的列表
        if (authRolePrefix.equals(prefix)) {
            // 处理角色授权信息
            List<AuthRole> roleList = new Gson().fromJson(authValue, new TypeToken<List<AuthRole>>() {
            }.getType());
            authList = roleList.stream().map(AuthRole::getRoleKey).collect(Collectors.toList());
        } else if (authPermissionPrefix.equals(prefix)) {
            // 处理权限授权信息
            List<AuthPermission> permissionList = new Gson().fromJson(authValue, new TypeToken<List<AuthPermission>>() {
            }.getType());
            authList = permissionList.stream().map(AuthPermission::getPermissionKey).collect(Collectors.toList());
        }
        return authList;
    }


}