package org.zyq.useful;

import org.apache.http.client.protocol.HttpClientContext;

public class User {
    private transient HttpClientContext httpContext = new HttpClientContext();
    private String user_name;
    private String password;

    public User() {

    }

    public User(HttpClientContext httpContext, String user_name, String password) {
        this.httpContext = httpContext;
        this.user_name = user_name;
        this.password = password;
    }

    public HttpClientContext getHttpContext() {
        return httpContext;
    }

    public void setHttpContext(HttpClientContext httpContext) {
        this.httpContext = httpContext;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}