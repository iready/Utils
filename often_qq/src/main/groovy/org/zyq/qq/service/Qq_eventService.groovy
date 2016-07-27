package org.zyq.qq.service

import org.zyq.qq.model.EventLog
import org.zyq.qq.model.User

import static EventLog.dao as evdao
import static User.dao as userDao

/**
 * Created by Administrator on 2016/5/5.
 */
class Qq_eventService {

    static Qq_eventService server = new Qq_eventService();

    List<User> find_time_or_not_esixt(String type, int sec, String friendID, String mysql_time_type) {
        return userDao.find("SELECT * FROM ( SELECT  u.* FROM qq_user u LEFT JOIN qq_event_log qe ON u.userId = qe.userId WHERE  qe.dest is NULL  or qe.dest<>? or  qe.id IS NULL UNION ALL SELECT u.* FROM qq_user u LEFT JOIN qq_event_log qe ON u.userId = qe.userId WHERE qe.type = ? AND timestampdiff($mysql_time_type,time,now())>=? AND qe.dest = ?) T WHERE T.status = 0 AND T.userId <> ?", friendID, type, sec, friendID, friendID);
    }

    void overwrite(User user, String type, String result, String dest) {
        List<EventLog> list = evdao.find("select * from ${evdao.tableName} where type=? and userId=? and dest=?", type, user.userId, dest);
        switch (list.size()) {
            case 0:
                new EventLog().set("time", new Date()).set("dest", dest).set("id", UUID.randomUUID().toString()).set("type", type).set("result", result).set("userId", user.userId).save();
                break;
            case 1:
                list.get(0).set("time", new Date()).update();
                break;
            default:
                list.each { it.delete() }
                overwrite(user, type, result, dest);
                break;
        }
    }

    EventLog find_by_sign(String type, String userId, String dest) {
        return EventLog.dao.findFirst("select * from ${EventLog.tableName} where type=? and userId=? and dest=? and  day(now())<>day(time)", type, userId, dest)
    }
}