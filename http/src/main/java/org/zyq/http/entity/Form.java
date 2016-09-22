package org.zyq.http.entity;

import org.apache.http.message.BasicNameValuePair;
import org.zyq.core.lang.Str;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Form extends TreeMap<String, String> {

    private String target;

    public List<BasicNameValuePair> getFormData(Map<String, String> map) {
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

    public Form set(String key, String value) {
        super.put(key, value);
        return this;
    }

    public String toUrl() {
        int i = 0;
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> map : this.entrySet()) {
            if (i == 0) {
                sb.append("?");
            }
            sb.append(map.getKey()).append("=").append(map.getValue());
            i++;
            if (i != this.size()) {
                sb.append("&");
            }
        }
        return sb.toString();
    }

    public String getTarget() {
        return target;
    }

    public Form setTarget(String target) {
        this.target = target;
        return this;
    }

    public Form parse(String url) {
        if (Str.notBlank(url)) {
            int fw = url.indexOf("?");
            if (fw > -1) {
                target = url.substring(0, fw);
                String lw = url.substring(fw + 1);
                Pattern pattern = Pattern.compile("(\\w+=\\w+&)");
                Matcher matcher = pattern.matcher(lw);
                while (matcher.find()) {
                    fjAndPut(matcher.group(0));
                }
                this.put(lw.substring(lw.lastIndexOf("&") + 1, lw.lastIndexOf("=")), lw.substring(lw.lastIndexOf("=") + 1));
            }
        }
        return this;
    }

    private void fjAndPut(String s) {
        this.put(s.substring(0, s.indexOf("=")), s.substring(s.indexOf("=") + 1, s.indexOf("&")));
    }

    public List<BasicNameValuePair> toNameValuePair() {
        return getFormData(this);
    }
}