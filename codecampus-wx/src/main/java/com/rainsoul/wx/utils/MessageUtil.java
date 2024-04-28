package com.rainsoul.wx.utils;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageUtil {
    /**
     * 解析XML字符串并将其转换为键值对映射。
     *
     * @param xmlStr 要解析的XML字符串。
     * @return 一个包含XML元素名称和文本内容的映射表。
     */
    public static Map<String, String> parseXml(final String xmlStr) {
        HashMap<String, String> hashMap = new HashMap<String, String>();
        // 将XML字符串转换为输入流以供解析
        try (InputStream inputStream = new ByteArrayInputStream(xmlStr.getBytes(StandardCharsets.UTF_8.name()))) {
            SAXReader saxReader = new SAXReader();
            // 使用SAXReader读取输入流并创建DOM文档对象
            Document document = saxReader.read(inputStream);
            Element rootElement = document.getRootElement();
            // 获取根元素的所有子元素
            List<Element> elementList = rootElement.elements();

            // 遍历子元素，将元素名称和文本内容添加到映射表中
            for (Element element : elementList) {
                hashMap.put(element.getName(), element.getText());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return hashMap;
    }
}
