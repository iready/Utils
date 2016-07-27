package org.zyq.qq.model;

import org.zyq.qq.model.base.BaseCookie;

/**
 * Created by Administrator on 2016/4/29.
 */
public class Cookie extends BaseCookie<Cookie> {
    public static final String tableName = "qq_cookie";
    public static final String QZone = "QZONE_login";
    public static final String QVip = "QVip";
    public static Cookie dao = new Cookie();

}
