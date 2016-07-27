package org.zyq.qq.service

import com.jfinal.plugin.activerecord.Page
import org.apache.http.protocol.HttpContext
import org.zyq.qq.model.User

import static User.dao as userDao
import static org.zyq.qq.service.Qq_cookieService.server as cookieServer

class Qq_userService {

    static Qq_userService userServer = new Qq_userService();

    List<List<User>> fenge(int size) {
        String sql = " from qq_user where status=0";
        Page<User> pa = userDao.paginate(1, size, 'select * ', sql);
        List<List<User>> list = new ArrayList<>();
        list.add(pa.list);
        for (int i = 2; i <= pa.totalPage; i++) {
            Page<User> pages = userDao.paginate(i, size, 'select * ', sql);
            list.add(pages.list);
        }
        return list;
    }

    List<User> find_all() {
        List<User> list = userDao.find("select * from ${userDao.tableName}");
        return list;
    }


    User findUserByUserId(String userId) {
        User qq_user = userDao.findFirst("select * from ${userDao.tableName} where userId=?", userId);
        if (qq_user == null) {
            println "无此用户"
            return null;
        }
        qq_user.set("user_useTime", new Date()).update();
        return qq_user;
    }


    boolean IsYouxiao(User user, String type) {
        return user != null && Qq_cookieService.server.find(user.userId, type, 5 * 60) != null;
    }

    void update_state(User user, int st) {
        user.set("status", st).update();
    }


    void updateHttpContent(User user, String type, HttpContext context) {
        User q = user;
        if (q != null) {
            cookieServer.overwriteCookie(user.userId, type, context);
        }
    }
}