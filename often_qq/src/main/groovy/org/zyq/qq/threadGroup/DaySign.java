package org.zyq.qq.threadGroup;

import org.zyq.qq.SysVar;
import org.zyq.qq.model.EventLog;
import org.zyq.qq.model.User;
import org.zyq.qq.service.Qq_eventService;

/*每日签到*/
public class DaySign implements Runnable {

    public void run() {
        Qq_eventService service = Qq_eventService.getServer();
        if (service.find_by_sign(EventLog.event_vip_sign, "981471846", "981471846") == null) {
            if (SysVar.activeUser.containsKey("981471846")) {
                User user = SysVar.activeUser.get("981471846");
                SysVar.userUtils.vip_mobile_login(user);
                SysVar.userUtils.vip_sign(user);
                service.overwrite(user, EventLog.event_vip_sign, "成功", user.getUserId());
            }
        }
    }
}
