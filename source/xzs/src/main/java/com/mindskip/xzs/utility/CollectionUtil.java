package com.mindskip.xzs.utility;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
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

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 通用字符串转列表（支持 List<Integer> 和 List<String>）
     * @param str 输入字符串（支持JSON数组格式或逗号分隔格式）
     * @param typeReference 目标类型引用（如 new TypeReference<List<Integer>>(){}
     * @return 转换后的列表，空输入返回空列表
     */
    public static <T> List<T> stringToList(String str, TypeReference<List<T>> typeReference, String delimiter) {
        if (StringUtils.isEmpty(str)) {
            return new ArrayList<>();
        }
        // 处理JSON数组格式
        if (str.trim().startsWith("[") && str.trim().endsWith("]")) {
            try {
                return objectMapper.readValue(str, typeReference);
            } catch (IOException e) {
                logger.error("JSON解析失败: {}", e.getMessage());
                return new ArrayList<>();
            }
        }
        // 使用自定义分隔符处理普通字符串
        String[] items = str.split(Pattern.quote(delimiter));
        List<T> list = new ArrayList<>(items.length);
        for (String item : items) {
            String trimmedItem = item.trim();
            if (typeReference.getType().getTypeName().contains("Integer")) {
                try {
                    list.add((T) Integer.valueOf(trimmedItem));
                } catch (NumberFormatException e) {
                    logger.error("整数转换失败: {}", e.getMessage());
                }
            } else {
                list.add((T) trimmedItem);
            }
        }
        return list;
    }
}
