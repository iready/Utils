package org.zyq.qq;

import com.jfinal.config.*;
import com.jfinal.kit.PropKit;
import com.jfinal.plugin.activerecord.ActiveRecordPlugin;
import com.jfinal.plugin.druid.DruidPlugin;
import com.jfinal.render.ViewType;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.quartz.SchedulerException;
import org.zyq.core.log4j.Lapender;
import org.zyq.qq.controller.Index;
import org.zyq.qq.model._MappingKit;
import org.zyq.qq.threadGroup.DaySign;
import org.zyq.qq.threadGroup.UserKeepActive;
import org.zyq.qq.threadGroup.ZoneBrush;

import java.util.concurrent.TimeUnit;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class WebConfig extends JFinalConfig {
    static Logger logger = Logger.getLogger(WebConfig.class);

    static DruidPlugin createDrid() {
        return new DruidPlugin(PropKit.get("url"), PropKit.get("userName"), PropKit.get("password").trim()).setValidationQuery("SELECT 1").set(5, 1, 20).setMaxActive(40).setMaxWait(10000).setValidationQuery("select 1").setTestOnBorrow(true).setTestWhileIdle(true).setTimeBetweenEvictionRunsMillis(10000);
    }

    public void configConstant(Constants me) {
        BasicConfigurator.configure(new Lapender());
        me.setDevMode(true);
        me.setViewType(ViewType.JSP);
        me.setBaseViewPath("/WEB-INF/view");
    }

    public void configRoute(Routes me) {
        me.add("/", Index.class);
    }

    public void configPlugin(Plugins me) {
        PropKit.use("db.properties");
        DruidPlugin C3p0Plugin = createDrid();
        me.add(C3p0Plugin);
        ActiveRecordPlugin arp = new ActiveRecordPlugin(C3p0Plugin);
        me.add(arp);
        arp.setShowSql(true);
        _MappingKit.mapping(arp);
    }

    public void afterJFinalStart() {
        super.afterJFinalStart();
        try {
            SysVar.scheduler.scheduleJob(newJob(UserKeepActive.class)
                    .withIdentity("userJob", "group1")
                    .build(), newTrigger().withIdentity("userTrigger", "group1")
                    .startNow()
                    .withSchedule(simpleSchedule().withIntervalInMinutes(5).repeatForever())
                    .build());
        } catch (Exception e) {
            logger.error(e);
        }
        SysVar.producer.scheduleAtFixedRate(new ZoneBrush(), 20, 30, TimeUnit.SECONDS);
    }

    public void beforeJFinalStop() {
        super.beforeJFinalStop();
        try {
            SysVar.scheduler.shutdown();
        } catch (SchedulerException e) {
            e.printStackTrace();
        }
        SysVar.producer.shutdownNow();
        SysVar.qzoneExecutor.shutdownNow();
        SysVar.userExecutor.shutdownNow();
    }

    public void configInterceptor(Interceptors me) {

    }

    public void configHandler(Handlers me) {

    }
}
