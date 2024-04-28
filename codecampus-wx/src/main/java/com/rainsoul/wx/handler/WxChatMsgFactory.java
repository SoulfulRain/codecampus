package com.rainsoul.wx.handler;

import org.springframework.stereotype.Component;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 微信聊天消息处理器工厂类
 */
@Component
public class WxChatMsgFactory {

    // 注入所有消息处理器
    @Resource
    private List<WxChatMsgHandler> wxChatMsgHandlerList;

    // 消息处理器映射，用于根据消息类型快速获取对应的处理器
    private Map<WxChatMsgTypeEnum, WxChatMsgHandler> handlerMap = new HashMap<>();

    /**
     * 根据消息类型获取对应的消息处理器
     * @param msgType 消息类型字符串
     * @return 对应的消息处理器，如果找不到则返回null
     */
    public WxChatMsgHandler getWxChatMsgHandler(String msgType) {
        // 根据消息类型字符串获取消息类型枚举常量
        WxChatMsgTypeEnum wxChatMsgTypeEnum = WxChatMsgTypeEnum.getByMsgType(msgType);
        // 根据消息类型枚举常量获取对应的消息处理器
        return handlerMap.get(wxChatMsgTypeEnum);
    }

    /**
     * 初始化方法，在所有属性被设置之后被调用
     * @throws Exception 初始化异常
     */
    public void afterPropertiesSet() throws Exception {
        // 遍历所有消息处理器，将每个处理器与其对应的消息类型放入消息处理器映射中
        for (WxChatMsgHandler wxChatMsgHandler : wxChatMsgHandlerList) {
            handlerMap.put(wxChatMsgHandler.getMsgType(), wxChatMsgHandler);
        }
    }

}

