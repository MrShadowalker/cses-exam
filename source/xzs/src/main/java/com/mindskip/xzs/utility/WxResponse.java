package com.mindskip.xzs.utility;


import java.io.Serializable;


/**
 * @version 3.5.0
 * @description: The type Wx response.
 * Copyright (C), 2020-2025, 武汉思维跳跃科技有限公司
 * @date 2021/12/25 9:45
 */
public class WxResponse implements Serializable {
    private static final long serialVersionUID = -8496869159673561976L;
    private String session_key;
    private String openid;
    private String unionid;
    private String errcode;
    private String errmsg;

    /**
     * Gets serial version uid.
     *
     * @return the serial version uid
     */
    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    /**
     * Gets session key.
     *
     * @return the session key
     */
    public String getSession_key() {
        return session_key;
    }

    /**
     * Sets session key.
     *
     * @param session_key the session key
     */
    public void setSession_key(String session_key) {
        this.session_key = session_key;
    }

    /**
     * Gets openid.
     *
     * @return the openid
     */
    public String getOpenid() {
        return openid;
    }

    /**
     * Sets openid.
     *
     * @param openid the openid
     */
    public void setOpenid(String openid) {
        this.openid = openid;
    }

    /**
     * Gets unionid.
     *
     * @return the unionid
     */
    public String getUnionid() {
        return unionid;
    }

    /**
     * Sets unionid.
     *
     * @param unionid the unionid
     */
    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }

    /**
     * Gets errcode.
     *
     * @return the errcode
     */
    public String getErrcode() {
        return errcode;
    }

    /**
     * Sets errcode.
     *
     * @param errcode the errcode
     */
    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    /**
     * Gets errmsg.
     *
     * @return the errmsg
     */
    public String getErrmsg() {
        return errmsg;
    }

    /**
     * Sets errmsg.
     *
     * @param errmsg the errmsg
     */
    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
