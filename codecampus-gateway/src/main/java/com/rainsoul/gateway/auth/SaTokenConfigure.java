package com.rainsoul.gateway.auth;

import cn.dev33.satoken.context.SaHolder;
import cn.dev33.satoken.reactor.filter.SaReactorFilter;
import cn.dev33.satoken.router.SaRouter;
import cn.dev33.satoken.stp.StpUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SaTokenConfigure {

    /**
     * 创建并配置SaReactorFilter Bean实例。
     * 该过滤器用于拦截请求并进行鉴权处理。
     *
     * @return 返回配置好的SaReactorFilter实例
     */
    @Bean
    public SaReactorFilter getSaReactorFilter() {
        return new SaReactorFilter()
                // 拦截所有请求
                .addInclude("/**")
                // 设置鉴权逻辑
                .setAuth(o -> {
                    // 打印请求路径
                    System.out.println("===========前端访问路径：" + SaHolder.getRequest().getRequestPath());
                    // 登录校验 -- 拦截所有路由，并排除/user/doLogin 用于开放登录
                     SaRouter.match("/auth/**", "/auth/user/doLogin", r -> StpUtil.checkRole("admin"));
                    // 对指定路径进行登录检查或权限检查
                    SaRouter.match("/oss/**", r -> StpUtil.checkLogin());
                    SaRouter.match("/subject/subject/add", r -> StpUtil.checkPermission("subject:add"));
                    SaRouter.match("/subject/**", r -> StpUtil.checkLogin());
                });
    }

}
