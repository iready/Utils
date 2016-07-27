package org.zyq.qq.model;

import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.zyq.qq.model.base.BaseUser;

public class User extends BaseUser<User> {
    public static String tableName = "qq_user";
    public static User dao = new User();
    private HttpContext httpContext = new BasicHttpContext();
    private String username;
    private String encryPass;
    private String gtk;
    private String sid;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public HttpContext getHttpContext() {
        return httpContext;
    }

    public User setHttpContext(HttpContext httpContext) {
        this.httpContext = httpContext;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public User setUsername(String username) {
        this.username = username;
        return this;
    }


    public String getEncryPass() {
        return encryPass;
    }

    public User setEncryPass(String encryPass) {
        this.encryPass = encryPass;
        return this;
    }

    public String getGtk() {
        return gtk;
    }

    public User setGtk(String gtk) {
        this.gtk = gtk;
        return this;
    }
}