package org.zyq.http.factory;

import org.apache.http.Consts;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.config.MessageConstraints;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.cookie.*;
import org.apache.http.impl.client.DefaultHttpRequestRetryHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.impl.cookie.BestMatchSpecFactory;
import org.apache.http.impl.cookie.BrowserCompatSpec;
import org.apache.http.impl.cookie.BrowserCompatSpecFactory;
import org.apache.http.protocol.HttpContext;
import org.zyq.http.entity.Config;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.nio.charset.CodingErrorAction;
import java.security.cert.CertificateException;

public class ClientFactory {
    private Config config;

    public ClientFactory(Config config) {
        this.config = config;
    }

    public HttpClient getHttpClient() {
        return ClientBuilder().build();
    }

    private HttpClientBuilder ClientBuilder() {
        HttpClientBuilder hp = null;
        //绕过安全验证
        try {
            CookieSpecProvider easySpecProvider = new CookieSpecProvider() {
                public CookieSpec create(HttpContext context) {
                    return new BrowserCompatSpec() {
                        public void validate(Cookie cookie, CookieOrigin origin) throws MalformedCookieException {
                        }
                    };
                }
            };
            SSLContext sc = SSLContext.getInstance("SSLv3");
            sc.init(null, new TrustManager[]{new X509TrustManager() {
                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                        String paramString) throws CertificateException {
                }

                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] paramArrayOfX509Certificate,
                        String paramString) throws CertificateException {
                }

                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return null;
                }
            }}, null);
            Registry<ConnectionSocketFactory> socketFactoryRegistry = RegistryBuilder.<ConnectionSocketFactory>create()
                    .register("http", PlainConnectionSocketFactory.INSTANCE)
                    .register("https", new SSLConnectionSocketFactory(sc))
                    .build();
            PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager(socketFactoryRegistry);
            ConnectionConfig connectionConfig = ConnectionConfig.custom().setMalformedInputAction(CodingErrorAction.IGNORE).setUnmappableInputAction(CodingErrorAction.IGNORE).setCharset(Consts.UTF_8)
                    .setMessageConstraints(MessageConstraints.DEFAULT).build();
            Registry<CookieSpecProvider> reg = RegistryBuilder.<CookieSpecProvider>create().register(CookieSpecs.DEFAULT, new BestMatchSpecFactory())
                    .register(CookieSpecs.DEFAULT, new BrowserCompatSpecFactory()).register("mySpec", easySpecProvider).build();
            cm.setDefaultConnectionConfig(connectionConfig);
            cm.setMaxTotal(100);
            cm.setDefaultMaxPerRoute(10);
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(config.getOutTimes()).setConnectTimeout(config.getOutTimes()).setCookieSpec("mySpec").build();
            hp = HttpClients.custom().setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/46.0.2490.71 Safari/537.36")
                    .setDefaultCookieSpecRegistry(reg).setConnectionManager(cm).setMaxConnPerRoute(5).setMaxConnTotal(5).setDefaultRequestConfig(requestConfig);
            hp.setRetryHandler(new DefaultHttpRequestRetryHandler(config.getRepeatCount(), false));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hp;
    }
}