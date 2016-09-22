package org.zyq.http;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.zyq.http.entity.Config;
import org.zyq.http.entity.Form;
import org.zyq.http.factory.ClientFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

public class DefaultHttpUtils implements HttpUtils {
    private HttpClient httpClient;
    private Config config;

    public DefaultHttpUtils(Config config) {
        this.config = config;
        httpClient = new ClientFactory(config).getHttpClient();
    }

    public String get(String url) throws IOException {
        return get(url, config.getHttpContext());
    }

    public String get(String url, Form param) throws IOException {
        return get(url + param.toUrl());
    }

    public String get(String url, HttpContext httpContext) throws IOException {
        return get(url, httpContext, new ResponseHandler<String>() {
            public String handleResponse(HttpResponse response) throws IOException {
                if (response.getStatusLine().getStatusCode() != 200)
                    System.out.println(response.getStatusLine());
                return EntityUtils.toString(response.getEntity(), config.getDefaultEncoding());
            }
        });
    }

    public <M> M get(String url, ResponseHandler<M> handler) throws IOException {
        if (isRighturl(url)) {
            HttpGet get = new HttpGet(url);
            get.setHeaders(config.getHeaders());
            return httpClient.execute(get, handler);
        } else
            throw new RuntimeException(new URISyntaxException(url, "无效URI"));
    }

    public String get(String url, HttpContext httpContext, Form param) throws IOException {
        return get(url + param.toUrl(), httpContext);
    }

    public <M> M get(String url, HttpContext httpContext, ResponseHandler<M> handler) throws IOException {
        if (isRighturl(url)) {
            HttpGet get = new HttpGet(url);
            get.setHeaders(config.getHeaders());
            return httpClient.execute(get, handler, httpContext);
        } else
            throw new RuntimeException(new URISyntaxException(url, "无效URI"));
    }

    public String post(String url, Form param) throws IOException {
        return post(url, config.getHttpContext(), param);
    }

    public String post(String url, HttpContext httpContext, Form param) throws IOException {
        return post(url, httpContext, param.toNameValuePair(), new ResponseHandler<String>() {
            public String handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
                if (response.getStatusLine().getStatusCode() != 200) {
                    System.out.println(response.getStatusLine());
                }
                return EntityUtils.toString(response.getEntity(), config.getDefaultEncoding());
            }
        });
    }

    public <M> M post(String url, HttpContext httpContext, List<BasicNameValuePair> params, ResponseHandler<M> handler) throws IOException {
        if (isRighturl(url)) {
            HttpPost post = new HttpPost(url);
            post.setEntity(new UrlEncodedFormEntity(params, config.getDefaultEncoding()));
            return httpClient.execute(post, handler, httpContext);
        } else
            throw new RuntimeException(new URISyntaxException(url, "无效URI"));
    }

    public JSONObject parseJson(String json) {

        return (JSONObject) JSONObject.parse(json);
    }

    public Form newMap() {
        return new Form();
    }

    public Long nowTime(Integer sub_length) {
        return Long.valueOf((System.currentTimeMillis() + "").substring(0, sub_length));
    }

    private boolean isRighturl(String uri) {
        try {
            new URI(uri);
            return true;
        } catch (URISyntaxException e) {
            return false;
        }
    }

}
