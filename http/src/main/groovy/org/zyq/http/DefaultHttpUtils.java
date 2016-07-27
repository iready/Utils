package org.zyq.http;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.util.EntityUtils;
import org.zyq.http.entity.Config;
import org.zyq.http.entity.Form;
import org.zyq.http.factory.ClientFactory;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class DefaultHttpUtils implements HttpUtils {
    private HttpClient httpClient;
    private Config config;

    public DefaultHttpUtils(Config config) {
        this.config = config;
        httpClient = new ClientFactory(config).getHttpClient();
    }

    public static List<BasicNameValuePair> getFormData(Map<String, String> map) {
        List<BasicNameValuePair> params = new ArrayList<BasicNameValuePair>() {

            public boolean add(BasicNameValuePair e) {
                if (e.getValue() == null) {
                    e = new BasicNameValuePair(e.getName(), "");
                }
                return super.add(e);
            }
        };
        for (Map.Entry<String, String> e : map.entrySet()) {
            params.add(new BasicNameValuePair(e.getKey(), e.getValue()));
        }
        return params;
    }

    private boolean isRighturl(String uri) {
        try {
            new URI(uri);
            return true;
        } catch (URISyntaxException e) {
            return false;
        }
    }

    public String get(String url) throws IOException {
        return get(url, config.getHttpContext());
    }


    public String get(String Url, Map<String, String> param) throws IOException {
        if (param instanceof Form) {
            return get(Url + ((Form) param).toUrl());
        }
        return null;
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

    public <M> M get(String url, HttpContext httpContext, ResponseHandler<M> handler) throws IOException {
        if (isRighturl(url)) {
            HttpGet get = new HttpGet(url);
            get.setHeaders(config.getHeaders());
            return httpClient.execute(get, handler, httpContext);
        } else
            throw new RuntimeException(new URISyntaxException(url, "无效URI"));
    }


    public String get(String Url, HttpContext httpContext, Map<String, String> param) throws IOException {
        if (param instanceof Form) {
            return get(Url + ((Form) param).toUrl(), httpContext);
        }
        return null;
    }

    public String post(String url, Map<String, String> param) throws IOException {

        return post(url, config.getHttpContext(), param);
    }

    public String post(String url, HttpContext httpContext, Map<String, String> param) throws IOException {
        if (isRighturl(url)) {
            HttpPost post = new HttpPost(url);
            post.setEntity(new UrlEncodedFormEntity((getFormData(param))));
            return httpClient.execute(post, new ResponseHandler<String>() {
                public String handleResponse(HttpResponse response) throws IOException {
                    if (response.getStatusLine().getStatusCode() != 200) {
                        System.out.println(response.getStatusLine());
                    }
                    return EntityUtils.toString(response.getEntity(), config.getDefaultEncoding());
                }
            }, httpContext);
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
}
