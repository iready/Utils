package org.zyq.qq.utils

import org.zyq.core.lang.Str
import org.zyq.core.lang.T
import org.zyq.http.HttpUtils
import org.zyq.http.entity.Form
import org.zyq.qq.model.Cookie
import org.zyq.qq.model.User
import org.zyq.qq.service.Qq_cookieService
import org.zyq.qq.service.Qq_userService

import static Qq_cookieService.server as cookieServer

class UserUtils extends BaseUtils {

    public UserUtils(HttpUtils _) {
        this._ = _;
    }

    private static final String VIP_LOGIN_UI = 'http://vip.qq.com/my/index.html';
    private static final String VIP_CHECK_URL = 'http://check.ptlogin2.qq.com/check';
    private static final String VIP_LOGIN_URL = 'http://ptlogin2.qq.com/login';
    private static final String VIP_SING_JS_URL = 'http://imgcache.qq.com/club/portal_new/js/my/sign_new.js';
    private static
    final String MB_QZONE_LOGIN_UI = "http://ui.ptlogin2.qzone.com/cgi-bin/login?appid=${m_appid}&daid=147&g_ut=3&pt_no_auth=1&pt_ttype=1&s_url=http://m.qzone.com/infocenter?g_f=275&style=9";
    private static final String MB_QZONE_CHECK_URL = 'http://check.ptlogin2.qq.com/check';
    private static final String MB_QZONE_Login_URL = 'http://ptlogin2.qq.com/login';
    private static final String m_appid = '549000929';
    private static final String m_ptlang = '2052';
    private static final String vip_aid = '8000201';
    private HttpUtils _;

    boolean qzone_mobile_login(User user) {
        long d1 = System.currentTimeMillis();
        if (base_login(user, MB_QZONE_LOGIN_UI, MB_QZONE_CHECK_URL, MB_QZONE_Login_URL, _.newMap().set('appid', m_appid).set('ptlang', m_ptlang).set('pt_tea', "1"), _.newMap().set('pt_randsalt', '0&ptredirect=1&from_ui=1&h=1&g=1').set('ptlang', m_ptlang).set('low_login_enable', '0').set('u1', 'http://m.qzone.com/infocenter?g_f=275&g_ut=3').set('g_ut', '3').set('fp', "loginerroralert&device=2&aid=${m_appid}&daid=5&pt_ttype=1&pt_3rd_aid=0&pt_uistyle=9"), Cookie.QZone)) {
            logger.info "${user.userId}登陆成功 gtk为${user.gtk} 登陆耗时：${T.time_diff_seconds(d1)}s"
            Qq_userService.userServer.updateHttpContent(user, Cookie.QZone, user.httpContext);
            Qq_userService.userServer.update_state(user, 0);
            return true;
        } else {
            Qq_userService.userServer.update_state(user, 1);
            return false;
        }
    }

    private boolean base_login(User user, String login_ui, String check_url, String login_url, Map<String, String> check_map, Map<String, String> loing_map, String type) {
        if (Qq_userService.userServer.IsYouxiao(user, type)) {
            user.httpContext.setAttribute("http.cookie-store", Qq_cookieService.server.findAsHttpContext(user, type))
            QQUtils.evalGtk(user);
//            logger.info "${user.userId}成功使用缓存登陆"
            return true;
        } else {
            _.get(login_ui, user.httpContext);
            def s = QQUtils.parseByRes _.get(check_url, user.httpContext, ((Form) check_map).set('uin', user.userId));
            if (s[1].length() > 5) {
                logger.error "${user.userId} 需要安全验证"
            }
            user.encryPass = QQUtils.getpass(s, user.password);
            def r = QQUtils.parseByRes _.get(login_url, user.httpContext, ((Form) loing_map).set('pt_vcode_v1', '0').set('u', user.userId).set('p', user.encryPass).set('verifycode', s[1]).set('pt_verifysession_v1', s[3].trim()))
            QQUtils.evalGtk(user);
            if (Str.notBlank(r[2])) {
                _.get(r[2], user.httpContext).find("\"sid\":\"([0-9a-zA-Z= ]+)\"", {
                    user.setSid(it[1]);
                })
            }
            if (r[0] != '0') {
                logger.error r.join(',')
                return false;
            } else {
                return true;
            }
        }
    }

    void vip_mobile_login(User user) {
        long d1 = System.currentTimeMillis();
        String js_var = '10152';
        if (base_login(user, VIP_LOGIN_UI, VIP_CHECK_URL, VIP_LOGIN_URL, _.newMap().set('aid', vip_aid).set('js_ver', js_var).set('js_type', '1').set('login_sig', '').set('pt_vcode', '1').set('pt_tea', '1').set('regmaster', ''), _.newMap().set('pt_randsalt', '0&ptredirect=1&from_ui=1&h=1&g=1').set('u1', VIP_LOGIN_UI).set('ptlang', m_ptlang).set('action', '').set('js_ver', js_var).set('js_type', '1').set('login_sig', '').set('pt_uistyle', '20').set('aid', vip_aid).set('daid', '18'), Cookie.QVip)) {
            logger.info "登陆成功 gtk为${QQUtils.evalGtk(user)} 耗时${T.time_diff_seconds(d1)}s"
        }
    }

    void vip_sign(User user) {
        def d = [52002, 23314];
        for (def s : d) {
            println _.get("http://iyouxi.vip.qq.com/ams3.0.php?actid=$s&g_tk=${user.gtk}")
        }
    }

}
