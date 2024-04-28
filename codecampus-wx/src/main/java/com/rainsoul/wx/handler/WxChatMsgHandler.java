package com.rainsoul.wx.handler;

import java.util.Map;

/**
 * 微信聊天消息处理器接口
 */
public interface WxChatMsgHandler {

    /**
     * 获取消息类型
     *
     * @return 消息类型枚举常量
     */
    WxChatMsgTypeEnum getMsgType();

    /**
     * 处理消息
     *
     * @param msgMap 包含消息内容的映射
     * @return 处理后的消息内容
     */
    String dealMsg(Map<String, String> msgMap);
}
