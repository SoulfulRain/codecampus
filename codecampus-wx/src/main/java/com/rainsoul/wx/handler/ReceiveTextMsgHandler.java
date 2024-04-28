package com.rainsoul.wx.handler;

import com.rainsoul.wx.redis.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 处理接收到的文本消息事件的消息处理器
 */
@Component
@Slf4j
public class ReceiveTextMsgHandler implements WxChatMsgHandler {

    // 关键词：用于判断消息是否需要处理为验证码
    private static final String KEY_WORD = "验证码";

    // 登录验证码的Redis键前缀
    private static final String LOGIN_PREFIX = "loginCode";

    // Redis工具类
    @Resource
    private RedisUtil redisUtil;

    /**
     * 获取消息类型
     * @return 消息类型枚举常量，表示文本消息
     */
    @Override
    public WxChatMsgTypeEnum getMsgType() {
        return WxChatMsgTypeEnum.TEXT_MSG;
    }

    /**
     * 处理文本消息事件的方法
     * @param messageMap 包含消息内容的映射
     * @return 处理后的消息内容，用于回复给用户
     */
    @Override
    public String dealMsg(Map<String, String> messageMap) {
        // 记录日志
        log.info("接收到文本消息事件");

        // 获取消息内容
        String content = messageMap.get("Content");

        // 判断消息是否为验证码
        if (!KEY_WORD.equals(content)) {
            return ""; // 如果不是验证码，不做处理，直接返回空字符串
        }

        // 获取发送者和接收者信息
        String fromUserName = messageMap.get("FromUserName");
        String toUserName = messageMap.get("ToUserName");

        // 生成随机验证码
        Random random = new Random();
        int num = random.nextInt(1000);
        String numKey = redisUtil.buildKey(LOGIN_PREFIX, String.valueOf(num));
        redisUtil.setNx(numKey, fromUserName, 5L, TimeUnit.MINUTES);

        // 构造回复内容
        String numContent = "您当前的验证码是：" + num + "！ 5分钟内有效";
        String replyContent = "<xml>\n" +
                "  <ToUserName><![CDATA[" + fromUserName + "]]></ToUserName>\n" +
                "  <FromUserName><![CDATA[" + toUserName + "]]></FromUserName>\n" +
                "  <CreateTime>12345678</CreateTime>\n" +
                "  <MsgType><![CDATA[text]]></MsgType>\n" +
                "  <Content><![CDATA[" + numContent + "]]></Content>\n" +
                "</xml>";

        return replyContent; // 返回验证码消息内容
    }
}
