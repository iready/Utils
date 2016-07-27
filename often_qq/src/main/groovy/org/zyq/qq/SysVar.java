package org.zyq.qq;

import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.zyq.http.DefaultHttpUtils;
import org.zyq.http.HttpUtils;
import org.zyq.http.entity.Config;
import org.zyq.qq.model.User;
import org.zyq.qq.utils.UserUtils;
import org.zyq.qq.utils.ZoneUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class SysVar {
    public static ScheduledThreadPoolExecutor producer = new ScheduledThreadPoolExecutor(2);
    public static Scheduler scheduler = null;
    public static ExecutorService userExecutor = Executors.newFixedThreadPool(5);
    public static ExecutorService qzoneExecutor = Executors.newCachedThreadPool();
    public static HttpUtils _ = new DefaultHttpUtils(new Config());
    public static UserUtils userUtils = new UserUtils(_);
    public static ZoneUtils zoneUtils = new ZoneUtils(_);
    public static Map<String, User> activeUser = new ConcurrentHashMap<>();

    static {
        try {
            SchedulerFactory schedFact = new org.quartz.impl.StdSchedulerFactory();
            scheduler = schedFact.getScheduler();
            scheduler.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}