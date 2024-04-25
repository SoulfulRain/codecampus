package com.rainsoul.gateway.exception;

import cn.dev33.satoken.exception.SaTokenException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rainsoul.gateway.entity.Result;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 网关全局异常处理器
 */
@Component
public class GatewayExceptionHandler implements ErrorWebExceptionHandler {

    private ObjectMapper objectMapper = new ObjectMapper();


    /**
     * 处理服务端异常，返回相应的错误信息。
     *
     * @param serverWebExchange 服务器web交换机，用于获取请求和响应信息。
     * @param throwable 异常对象，表示发生的错误。
     * @return 返回一个Mono<Void>对象，表示异步处理结果，无返回值。
     */
    @Override
    public Mono<Void> handle(ServerWebExchange serverWebExchange, Throwable throwable) {
        ServerHttpRequest request = serverWebExchange.getRequest();
        ServerHttpResponse response = serverWebExchange.getResponse();
        Integer code = 200; // 默认响应状态码
        String message = ""; // 默认响应消息

        // 根据不同的异常类型设置不同的响应状态码和消息
        if (throwable instanceof SaTokenException) {
            code = 401; // 用户无权限状态码
            message = "用户无权限";
            throwable.printStackTrace();
        } else if (throwable instanceof Exception) {
            code = 500; // 系统异常状态码
            message = "系统异常";
        }
        Result result = Result.fail(code, message); // 构造错误结果对象

        // 设置响应头为JSON类型
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);

        // 将错误结果对象写入响应体并返回
        return response.writeWith(Mono.fromSupplier(() -> {
            DataBufferFactory dataBufferFactory = response.bufferFactory();
            byte[] bytes = null;
            try {
                bytes = objectMapper.writeValueAsBytes(result); // 将结果对象序列化为JSON字节流
            } catch (JsonProcessingException e) {
                e.printStackTrace(); // 处理JSON序列化异常
            }
            return dataBufferFactory.wrap(bytes); // 包装字节流为数据缓冲区并返回
        }));
    }
}
