package org.zyq.qq.threadGroup;

import com.jfinal.plugin.activerecord.Db;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.zyq.qq.SysVar;
import org.zyq.qq.model.User;
import org.zyq.qq.service.Qq_userService;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class UserKeepActive implements Job {
    static {
        Db.update("update qq_user set status=0");
    }

    public void execute(JobExecutionContext context) throws JobExecutionException {
        try {
            for (final List<User> list : Qq_userService.getUserServer().fenge(3)) {
//            SysVar.userExecutor.execute(new Runnable() {
//                @Override
//                public void run() {
                for (User user : list) {
                    try {
                        if (SysVar.userUtils.qzone_mobile_login(user)) {
                            SysVar.activeUser.put(user.getUserId(), user);
                        } else {
                            if (SysVar.activeUser.containsKey(user.getUserId())) {
                                SysVar.activeUser.remove(user.getUserId());
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                    }
                }
//                }
//            });
            }
            SysVar.producer.scheduleAtFixedRate(new DaySign(), 0, 1, TimeUnit.HOURS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
