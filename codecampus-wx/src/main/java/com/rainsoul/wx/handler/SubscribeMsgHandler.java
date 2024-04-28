package com.rainsoul.wx.handler;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.Map;

/**
 * 处理用户关注事件的消息处理器
 */
@Component
@Slf4j
public class SubscribeMsgHandler implements WxChatMsgHandler {

    /**
     * 获取消息类型
     * @return 消息类型枚举常量，表示用户关注事件
     */
    @Override
    public WxChatMsgTypeEnum getMsgType() {
        return WxChatMsgTypeEnum.SUBSCRIBE;
    }

    /**
     * 处理用户关注事件的方法
     * @param msgMap 包含消息内容的映射
     * @return 处理后的消息内容，用于回复给用户
     */
    @Override
    public String dealMsg(Map<String, String> msgMap) {
        // 记录日志
        log.info("触发用户关注事件");

        // 获取发送者和接收者信息
        String fromUserName = msgMap.get("FromUserName");
        String toUserName = msgMap.get("ToUserName");

        // 构造欢迎消息内容
        String subscribeContent = "欢迎关注RainSoul的公众号，欢迎来到程序员面试论坛社区";
        String content = "<xml>\n" +
                "<ToUserName><![CDATA[" + fromUserName + "]]></ToUserName>\n" +
                "<FromUserName><![CDATA[" + toUserName + "]]></FromUserName>\n" +
                "<CreateTime>" + System.currentTimeMillis() + "</CreateTime>\n" +
                "<MsgType><![CDATA[text]]></MsgType>\n" +
                "<Content><![CDATA[" + subscribeContent + "]]></Content>\n" +
                "</xml>";
        return content;
    }
}
