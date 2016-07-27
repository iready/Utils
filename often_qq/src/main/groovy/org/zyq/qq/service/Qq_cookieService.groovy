package org.zyq.qq.service

import org.apache.commons.io.FileUtils
import org.apache.http.client.CookieStore
import org.apache.http.protocol.HttpContext
import org.zyq.core.lang.FUtils
import org.zyq.qq.model.Cookie
import org.zyq.qq.model.User

import static Cookie.dao

class Qq_cookieService {
    static Qq_cookieService server = new Qq_cookieService();

    public Cookie find(String userId, String type, int time_diff) {
        return dao.findFirst("select * from qq_cookie where userId=? and  type=? and timestampdiff(SECOND,time,now())<=?", userId, type, time_diff);
    }

    public void overwriteCookie(String userId, String type, HttpContext context) throws IOException {
        try {
            List<Cookie> list = dao.find("select * from qq_cookie where userId=? and type=?", userId, type);
            File file = new File(FUtils.getTempDir(), "mx.oos");
            ObjectOutputStream oos = new ObjectOutputStream(FileUtils.openOutputStream(file));
            oos.writeObject(((CookieStore) context.getAttribute("http.cookie-store")));
            oos.close();
            byte[] b = FUtils.readFileAndDeleteFile(file);
            if (list.size() == 0) {
                Cookie qq_cookie = new Cookie();
                qq_cookie.set("userId", userId).set("type", type).set("cookie_content", b).set("time", new Date()).save();
            } else if (list.size() == 1) {
                list.get(0).set("cookie_content", b).set("time", new Date()).update();
            } else if (list.size() > 1) {
                for (Cookie qq_cookie : list) {
                    qq_cookie.delete();
                }
                overwriteCookie(userId, type, context);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public CookieStore findAsHttpContext(User user, String type) {
        Cookie q = find(user.userId, type, 15 * 60);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(q.getBytes("cookie_content"));
        try {
            ObjectInputStream ois = new ObjectInputStream(byteArrayInputStream);
            return (CookieStore) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
