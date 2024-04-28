package com.rainsoul.wx.handler;

/**
 * 微信聊天消息类型枚举
 */
public enum WxChatMsgTypeEnum {

    // 用户关注事件类型
    SUBSCRIBE("event.subscribe", "用户关注事件"),

    // 文本消息类型
    TEXT_MSG("text", "文本消息");

    // 消息类型字符串表示
    private String msgType;

    // 消息类型描述
    private String desc;

    // 构造方法
    private WxChatMsgTypeEnum(String msgType, String desc) {
        this.msgType = msgType;
        this.desc = desc;
    }

    /**
     * 根据消息类型字符串获取对应的枚举常量
     * @param msgType 消息类型字符串
     * @return 对应的枚举常量，如果找不到则返回null
     */
    public static WxChatMsgTypeEnum getByMsgType(String msgType) {
        for (WxChatMsgTypeEnum wxChatMsgTypeEnum : WxChatMsgTypeEnum.values()) {
            if (wxChatMsgTypeEnum.msgType.equals(msgType)) {
                return wxChatMsgTypeEnum;
            }
        }
        return null;
    }
}

