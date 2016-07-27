package org.zyq.qq

import com.jfinal.kit.Prop
import com.jfinal.kit.PropKit
import com.jfinal.plugin.activerecord.ActiveRecordPlugin
import com.jfinal.plugin.druid.DruidPlugin
import org.apache.log4j.Logger
import org.zyq.http.DefaultHttpUtils
import org.zyq.http.HttpUtils
import org.zyq.http.entity.Config
import org.zyq.qq.model.Cookie
import org.zyq.qq.model.EventLog
import org.zyq.qq.model.User

class Cofig {
    public static Prop prop;
    private static Logger logger = Logger.getLogger(Cofig.class);
    public static HttpUtils _ = new DefaultHttpUtils(new Config());
    static {
        try {
//            BasicConfigurator.configure(new Lapender());
            prop = PropKit.use("db.properties");
            DruidPlugin zyqDruid = new DruidPlugin(prop.get("url"), prop.get("userName"), prop.get("password")).setValidationQuery("SELECT 1").set(5, 1, 20).setMaxActive(40).setMaxWait(10000).setValidationQuery("select 1").setTestOnBorrow(true).setTestWhileIdle(true).setTimeBetweenEvictionRunsMillis(10000);
            ActiveRecordPlugin sendMsgARP = new ActiveRecordPlugin(zyqDruid).setShowSql(true);
            sendMsgARP.addMapping(User.tableName, User.class);
            sendMsgARP.addMapping(Cookie.tableName, Cookie.class);
            sendMsgARP.addMapping(EventLog.tableName, EventLog.class);
            zyqDruid.start();
            sendMsgARP.start();
        } catch (Exception e) {
            e.printStackTrace();
            logger.error(e);
            System.exit(1);
        }
    }
}
