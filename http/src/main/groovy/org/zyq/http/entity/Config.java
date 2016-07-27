package org.zyq.http.entity;


import org.apache.http.Header;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;

public class Config {
    private String defaultEncoding = "UTF-8";
    private HttpContext httpContext = new BasicHttpContext();
    private Header[] headers = new Header[]{};
    private int repeatCount = 2;
    private int outTimes = 10000;

    public String getDefaultEncoding() {
        return defaultEncoding;
    }

    public Config setDefaultEncoding(String defaultEncoding) {
        this.defaultEncoding = defaultEncoding;
        return this;
    }

    public HttpContext getHttpContext() {
        return httpContext;
    }

    public Config setHttpContext(HttpContext httpContext) {
        this.httpContext = httpContext;
        return this;
    }

    public Header[] getHeaders() {
        return headers;
    }

    public Config setHeaders(Header[] headers) {
        this.headers = headers;
        return this;
    }

    public int getRepeatCount() {
        return repeatCount;
    }

    public Config setRepeatCount(int repeatCount) {
        this.repeatCount = repeatCount;
        return this;
    }

    public int getOutTimes() {
        return outTimes;
    }

    public Config setOutTimes(int outTimes) {
        this.outTimes = outTimes;
        return this;
    }
}
