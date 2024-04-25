package com.rainsoul.gateway.filter;

import cn.dev33.satoken.stp.SaTokenInfo;
import cn.dev33.satoken.stp.StpUtil;
import com.google.gson.Gson;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 登录拦截器
 */
@Component
@Slf4j
public class LoginFilter implements GlobalFilter {

    /**
     * 对通过网关的请求进行过滤，如果请求是登录请求则直接通过，否则校验用户登录状态。
     *
     * @param exchange 服务器web交换机，提供请求和响应的交互接口
     * @param chain 网关过滤器链，用于继续处理或终止请求链
     * @return Mono<Void> 表示异步处理结果，无返回值
     * @throws Exception 抛出异常被捕获并处理
     */
    @Override
    @SneakyThrows
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        ServerHttpRequest request = exchange.getRequest();
        ServerHttpResponse response = exchange.getResponse();
        ServerHttpRequest.Builder mutate = request.mutate();

        // 获取并记录请求的URL
        String url = request.getURI().getPath();
        log.info("LoginFilter.filter.url:{}", url);

        // 如果是登录请求，则直接通过
        if (url.equals("/user/doLogin")) {
            return chain.filter(exchange);
        }

        // 获取用户登录信息
        SaTokenInfo tokenInfo = StpUtil.getTokenInfo();
        log.info("LoginFilter.filter.url:{}", new Gson().toJson(tokenInfo));

        // 从token中提取登录ID，并添加到请求头中
        String loginId = (String) tokenInfo.getLoginId();
        mutate.header("loginId", loginId);

        // 继续处理请求，但带有修改过的请求头
        return chain.filter(exchange.mutate().request(mutate.build()).build());
    }


}
