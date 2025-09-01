package com.mindskip.xzs.utility;


import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * @version 3.5.0
 * @description: The type Wx util.
 * Copyright (C), 2020-2025, 武汉思维跳跃科技有限公司
 * @date 2021/12/25 9:45
 */
public class WxUtil {
    private static final Logger logger = LoggerFactory.getLogger(WxUtil.class);
    private static final String openIdUrl = "https://api.weixin.qq.com/sns/jscode2session?appid=%s&secret=%s&js_code=%s&grant_type=authorization_code";

    /**
     * Gets open id.
     *
     * @param appId  the app id
     * @param secret the secret
     * @param code   the code
     * @return the open id
     */
    public static String getOpenId(String appId, String secret, String code) {
        WxResponse wxResponse = getWxAuthInfo(appId, secret, code);
        return wxResponse != null ? wxResponse.getOpenid() : null;
    }

    /**
     * 获取微信授权信息
     *
     * @param appId  微信小程序AppId
     * @param secret 微信小程序AppSecret
     * @param code   微信小程序登录凭证
     * @return WxResponse 微信授权响应信息
     */
    public static WxResponse getWxAuthInfo(String appId, String secret, String code) {
        try (CloseableHttpClient httpClient = HttpClientBuilder.create().build()) {
            String requestUrl = String.format(openIdUrl, appId, secret, code);
            HttpGet httpGet = new HttpGet(requestUrl);
            HttpEntity responseEntity = httpClient.execute(httpGet).getEntity();
            if (responseEntity != null) {
                String responseStr = EntityUtils.toString(responseEntity);
                logger.info("微信授权接口响应: {}", responseStr);
                
                WxResponse wxResponse = JsonUtil.toJsonObject(responseStr, WxResponse.class);
                if (wxResponse != null) {
                    // 检查是否有错误信息
                    if (wxResponse.getErrcode() != null && !"0".equals(wxResponse.getErrcode())) {
                        logger.error("微信授权失败, errcode: {}, errmsg: {}", wxResponse.getErrcode(), wxResponse.getErrmsg());
                        return null;
                    }
                    // 检查是否包含openid
                    if (wxResponse.getOpenid() != null && !wxResponse.getOpenid().isEmpty()) {
                        return wxResponse;
                    }
                }
            }
        } catch (IOException e) {
            logger.error("调用微信授权接口异常", e);
        }
        return null;
    }
}
