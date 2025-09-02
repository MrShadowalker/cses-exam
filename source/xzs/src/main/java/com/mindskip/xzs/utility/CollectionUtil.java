package com.mindskip.xzs.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 *
 * @author Shadowalker
 */
public class CollectionUtil {

    private static final Logger logger = LoggerFactory.getLogger(CollectionUtil.class);

    /**
     * 将整数列表转换为逗号分隔的字符串，适合存储到VARCHAR字段
     * @param list 整数列表（可为null或空列表）
     * @return 逗号分隔的字符串，空列表返回空字符串，null返回"null"
     */
    public static String intListToString(List<Integer> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        return list.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(","));
    }

    /**
     * 将整数列表转换为逗号分隔的字符串，适合存储到VARCHAR字段，结果添加方括号
     * @param list 整数列表（可为null或空列表）
     * @return 逗号分隔的字符串，空列表返回空字符串，null返回"null"
     */
    public static String intListToStringWithBracket(List<Integer> list) {
        if (list == null) {
            return null;
        }

        if (list.isEmpty()) {
            return "[]";
        }

        return "[" + list.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(",")) + "]";
    }

    /**
     * 将形如 '[xx,xx,xx]' 或 'x,xx,xxx' 的字符串转化为数字列表
     * @param str 输入字符串
     * @return 整数列表，无效格式返回null
     */
    public static List<Integer> stringToIntList(String str) {
        if (str == null || str.trim().isEmpty() || "null".equalsIgnoreCase(str)) {
            return null;
        }

        // 处理带方括号的格式
        String content = str.trim();
        if (content.startsWith("[") && content.endsWith("]")) {
            content = content.substring(1, content.length() - 1).trim();
        }

        // 处理空内容
        if (content.isEmpty()) {
            return new ArrayList<>(); // 优化：返回空列表而非null
        }

        // 安全解析数字（修复正则表达式错误）
        try {
            return Arrays.stream(content.split(","))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty()) // 新增：过滤空字符串
                    .map(Integer::parseInt)
                    .collect(Collectors.toList());
        } catch (NumberFormatException e) {
            logger.error("Failed to parse integer list from string: {}", str, e);
            return null;
        }
    }
}
