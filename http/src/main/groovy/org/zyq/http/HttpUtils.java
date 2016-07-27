package org.zyq.http;

import com.alibaba.fastjson.JSONObject;
import org.apache.http.client.ResponseHandler;
import org.apache.http.protocol.HttpContext;
import org.zyq.http.entity.Form;

import java.io.IOException;
import java.util.Map;

public interface HttpUtils {

    String get(String Url) throws IOException;

    String get(String Url, Map<String, String> param) throws IOException;

    String get(String Url, HttpContext httpContext) throws IOException;

    String get(String Url, HttpContext httpContext, Map<String, String> param) throws IOException;

    <M> M get(String url, HttpContext httpContext, ResponseHandler<M> handler) throws IOException;

    String post(String Url, Map<String, String> param) throws IOException;

    String post(String Url, HttpContext httpContext, Map<String, String> param) throws IOException;

    JSONObject parseJson(String json);

    Form newMap();

    Long nowTime(Integer sub_length);
}
