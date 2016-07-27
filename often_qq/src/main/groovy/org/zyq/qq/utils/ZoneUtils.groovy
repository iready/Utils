package org.zyq.qq.utils

import org.apache.http.HttpResponse
import org.apache.http.client.ClientProtocolException
import org.apache.http.client.ResponseHandler
import org.zyq.core.algorithm.URLkit
import org.zyq.http.HttpUtils
import org.zyq.qq.Cofig
import org.zyq.qq.SysVar
import org.zyq.qq.model.EventLog
import org.zyq.qq.model.User
import org.zyq.qq.service.Qq_userService

import static org.zyq.qq.service.Qq_eventService.server as eventServer

class ZoneUtils extends BaseUtils {
    private static String combo = 'https://mobile.qzone.qq.com/combo';
    private static String dolike = 'http://w.qzone.qq.com/cgi-bin/likes/internal_dolike_app';
    HttpUtils _;

    ZoneUtils(HttpUtils httpUtils) {
        this._ = httpUtils;
    }

    /*更新全表的用户状态*/

    void UPST() {
        UserUtils userUtils = new UserUtils(_);
        Qq_userService.userServer.find_all().each {
            userUtils.qzone_mobile_login(it);
        }
    }


    void footFreindZone(String friend) {
        eventServer.find_time_or_not_esixt(EventLog.event_foot, 10, friend, "MINUTE").each {
            footFreindZone(it, friend);
        }
    }

    void footFreindZone(User user, String friend) {
        try {
            _.get("$combo?g_tk=${user.gtk}&hostuin=$friend&action=1&g_f=275&refresh_type=1&res_type=2&format=json${user.sid != null ? "&sid=" + URLkit.encode(user.sid) : ""}", user.httpContext, new ResponseHandler() {
                Object handleResponse(HttpResponse response) throws ClientProtocolException, IOException {
                    if (response.statusLine.statusCode == 200) {
                        eventServer.overwrite(user, EventLog.event_foot, '成功', friend)
                    } else {
                        logger.info "${user.userId}访问${friend}失败，${user.userId}失活"
                        SysVar.activeUser.remove(user.userId);
                    }
                    return null
                }
            });
        } catch (Exception e) {
            logger.error("超时", e)
        }
    }

    public void dianzan(User user) {
//        Map<String, String> s = _.newMap().parse("?qzreferrer=http: //user.qzone.qq.com/${user.userId}&opuin=${user.userId}&from=1&appid=311&typeid=0&abstime=1462164327&fid=a0275c1967db2657f6e70b00&active=0&fupdate=1");
//        s.curkey = 'http://user.qzone.qq.com/425469856/mood/a0275c1967db2657f6e70b00';
//        s.unikey = 'http://user.qzone.qq.com/425469856/mood/a0275c1967db2657f6e70b00';
//        println _.get("http://user.qzone.qq.com/p/ic2.s6/cgi-bin/feeds/cgi_get_feeds_count.cgi?uin=425469856&rd=0.4665680201728959&g_tk=312263271",user.httpContext)
//        println _.get("${dolike}?g_tk=${user.gtk}", user.httpContext)
    }

    public static void main(String[] args) {
        Class.forName("org.zyq.qq.Cofig");
        HttpUtils _ = Cofig._;
//        User user1 = new User("2753139004", "hhdmxy");
//        User user1 = new User("425469856", "!!!???");
//        User user1 = new User("981471846", "hhdmxy");
//        UserUtils userUtils = new UserUtils(_);
//        userUtils.qzone_mobile_login(user1);
//        executorService.execute(new Runnable() {
//            void run() {
//                ZoneUtils s = new ZoneUtils(_);
//                while (true) {
//                    s.footFreindZone("981471846");
//                    TimeUnit.SECONDS.sleep(30);
//                }
//            }
//        })
//        user1.httpContext.cookieStore.getCookies().findAll {
//            return it.domain == "qzone.com"
//        }.each {
//            println "${it.name}:${it.value}----${it.domain}"
//        }
//        userUtils.vip_mobile_login(user1);
//        userUtils.vip_sign(user1)
//        new ZoneUtils(_).UPST();
    }
}
