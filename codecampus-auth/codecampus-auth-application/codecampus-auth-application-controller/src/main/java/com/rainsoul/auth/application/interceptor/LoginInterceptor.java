package com.rainsoul.auth.application.interceptor;

import com.rainsoul.auth.context.LoginContextHolder; // 引入登录上下文持有者
import org.apache.commons.lang3.StringUtils; // 引入Apache Commons Lang工具类
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.HandlerInterceptor; // 引入Spring MVC的HandlerInterceptor接口

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 登录拦截器，用于检查并处理用户登录状态
 */
public class LoginInterceptor implements HandlerInterceptor {

    /**
     * 在处理请求之前进行拦截，检查登录状态并将登录信息存入上下文持有者中
     * @param request HttpServletRequest对象，用于获取请求头信息
     * @param response HttpServletResponse对象，用于返回响应
     * @param handler 处理请求的处理器对象
     * @return 若登录信息不为空，则返回true，否则返回false
     * @throws Exception 可能抛出的异常
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从请求头中获取登录ID
        String loginId = request.getHeader("loginId");
        // 若登录ID不为空，则将其存入登录上下文持有者中
        if (StringUtils.isNotBlank(loginId)) {
            LoginContextHolder.set("loginId", loginId);
        }
        // 返回true，表示继续执行后续的拦截器和处理器
        return true;
    }

    /**
     * 在请求处理完成后进行拦截，清除登录信息
     * @param request HttpServletRequest对象，用于获取请求信息
     * @param response HttpServletResponse对象，用于返回响应
     * @param handler 处理请求的处理器对象
     * @param ex 异常对象，可能为空
     * @throws Exception 可能抛出的异常
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        // 清除登录上下文持有者中的登录信息
        LoginContextHolder.remove();
    }

}
