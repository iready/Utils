package org.zyq.baidu.entity;

/**
 * Created by zouyq on 2016/4/9.
 */
public class BDUser extends org.zyq.useful.User {
    private String token;
    private String rsakey;

    public String getRsakey() {
        return rsakey;
    }

    public void setRsakey(String rsakey) {
        this.rsakey = rsakey;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
