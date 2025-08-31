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


    public static List<Integer> stringToIntList(String str) {
        if (str == null || str.isEmpty()) {
            return new ArrayList<>();
        }

        // 1. 验证格式
        if (!str.startsWith("[") || !str.endsWith("]")) {
            logger.warn("Invalid list format: {}", str);
            return new ArrayList<>();
        }

        // 2. 去除首尾中括号
        String content = str.substring(1, str.length() - 1);

        // 3. 处理空内容（如 "[]"）
        if (content.isEmpty()) {
            return new ArrayList<>();
        }

        // 4. 安全解析数字
        try {
            return Arrays.stream(content.split(",*"))
                    .map(Integer::parseInt)
                    .collect(Collectors.toCollection(ArrayList::new));
        } catch (NumberFormatException e) {
            logger.error(e.getMessage(), e);
            return new ArrayList<>();
        }
    }
}
