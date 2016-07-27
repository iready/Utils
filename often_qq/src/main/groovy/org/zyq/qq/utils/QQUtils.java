package org.zyq.qq.utils;

import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;
import org.apache.http.protocol.HttpContext;
import org.zyq.qq.model.User;

import javax.script.Invocable;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zouyq on 2016/3/13.
 */
public class QQUtils extends BaseUtils {

    static String[] parseByRes(String str) {
        Matcher matcher = Pattern.compile("\\((.+)\\)").matcher(str);
        if (matcher.find()) {
            str = matcher.group(1).replace("'", "");
        }
        return str.split(",");
    }

    public static String getpass(String[] arr, String password) throws Exception {
        InputStreamReader isr_vgm = new InputStreamReader(QQUtils.class.getResourceAsStream("/js/vgm.js")),
                isr_tea = new InputStreamReader(QQUtils.class.getResourceAsStream("/js/tea.js")),
                isr_md5 = new InputStreamReader(QQUtils.class.getResourceAsStream("/js/md5.js")),
                isr_rsa = new InputStreamReader(QQUtils.class.getResourceAsStream("/js/rsa.js"));
        try {
            String type = "javascript", pas = null, t = password, e = arr[2], n = arr[1];
            ScriptEngine vgm = new ScriptEngineManager().getEngineByName(type), teaEngine = new ScriptEngineManager().getEngineByName(type), md5Engine = new ScriptEngineManager().getEngineByName(type), rsaEngine = new ScriptEngineManager().getEngineByName(type);
            vgm.eval(isr_vgm);
            teaEngine.eval(isr_tea);
            md5Engine.eval(isr_md5);
            md5Engine.eval("function xq_md5(o) {var b='" + arr[2] + "';return md5(o + b);}");
            rsaEngine.eval(isr_rsa);
            Invocable vgminvoke = (Invocable) vgm;
            Invocable teainvoke = (Invocable) teaEngine;
            Invocable md5invoke = (Invocable) md5Engine;
            Invocable rsainvoke = (Invocable) rsaEngine;
            Object o = md5invoke.invokeFunction("md5", t);
            Object r = md5invoke.invokeFunction("hexchar2bin", o);
            Object a = md5invoke.invokeFunction("xq_md5", r);
            Object s = rsainvoke.invokeFunction("xq_a1", r);
            String p = (String) vgminvoke.invokeFunction("xq_a2", s);
            Object c = teainvoke.invokeFunction("xq_t1", n);
            String u = (String) vgminvoke.invokeFunction("xq_a3", c);
            Object l;
            for (; u.length() < 4; ) {
                u = "0" + u;
            }
            for (; p.length() < 4; ) {
                p = "0" + p;
            }
            l = teainvoke.invokeFunction("xq_t2", a, p, s, e.replace("\\x", ""), u, c);
            pas = (String) vgminvoke.invokeFunction("xq_a4", l, t);
            return pas;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            try {
                isr_vgm.close();
                isr_md5.close();
                isr_vgm.close();
                isr_rsa.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static String evalGtk(User user) {
        InputStreamReader isr = null;
        try {
            ScriptEngine vgm = new ScriptEngineManager().getEngineByName("javascript");
            isr = new InputStreamReader(QQUtils.class.getResourceAsStream("/js/vgm.js"));
            vgm.eval(isr);
            Invocable vgminvoke = (Invocable) vgm;
            HttpContext hc = user.getHttpContext();
            for (Cookie c : ((CookieStore) hc.getAttribute("http.cookie-store")).getCookies()) {
                if (c.getName().equals("skey")) {
                    user.setGtk(vgminvoke.invokeFunction("getGTK", c.getValue()) + "");
                }
            }
            return user.getGtk();
        } catch (Exception e) {
            logger.error(e);
            e.printStackTrace();
        } finally {
            try {
                if (isr != null)
                    isr.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
