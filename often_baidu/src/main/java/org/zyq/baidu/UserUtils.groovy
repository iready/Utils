package org.zyq.baidu

import org.apache.log4j.BasicConfigurator
import org.zyq.baidu.entity.BDUser
import org.zyq.core.algorithm.RSA
import org.zyq.core.log4j.Lapender
import org.zyq.http.DefaultHttpUtils
import org.zyq.http.HttpUtils
import org.zyq.http.entity.Config

class UserUtils {
    private HttpUtils _;
    private BDUser bdUser;

    UserUtils(BDUser bdUser) {
        this.bdUser = bdUser;
        _ = new DefaultHttpUtils(new Config().setHttpContext(bdUser.httpContext));
    }

    String getToken() {
        _.get("http://www.baidu.com", bdUser.httpContext);
        _.get("https://passport.baidu.com/v2/api/?getapi&class=login&tpl=mn&tangram=true", bdUser.httpContext).find("login_token='(\\w+)';", {
            bdUser.token = it[1];
        })
    }

    String encrypt() {
        String p = _.get("https://passport.baidu.com/v2/getpublickey?token=${bdUser.token}&tpl=mn&apiver=v3", bdUser.httpContext);
        p.find("-----BEGIN PUBLIC KEY-----(.*)-----END PUBLIC KEY-----", {
            bdUser.password = RSA.encode(it[1].replace("\\n", "").replace("\\/", "/"), bdUser.password);
        })
        p.find("\"key\":'(.+)'}", { bdUser.rsakey = it[1] })
    }

    boolean login() {
        getToken();
        encrypt();
        _.post("https://passport.baidu.com/v2/api/?login", bdUser.httpContext, _.newMap().set("mem_pass", "on").set("crypttype", "12").set("loginmerge", "true").set("apiver", "v3").set("tpl", "mn").set("token", bdUser.token).set("safeflg", "0").set("u", "http://pan.baidu.com/").set("detect", "1").set("gid", "").set("quick_user", "0").set("logintype", "basicLogin").set("logLoginType", "pc_loginBasic").set("idc", "").set("loginmerge", "true").set("rsakey", bdUser.rsakey).set("username", bdUser.user_name).set("password", bdUser.password).set("isPhone", "false").set("loginType", ""));
        return true;
    }

    void run() {

    }

    public static void main(String[] args) {
        BasicConfigurator.configure(new Lapender());
        BDUser bdUser = new BDUser();
        bdUser.user_name = "13299266512";
        bdUser.password = "hehe981471846";
        new UserUtils(bdUser).login();
    }
}
