package org.zyq.qq.model;

import org.zyq.qq.model.base.BaseEventLog;

public class EventLog extends BaseEventLog<EventLog> {
    public static final String tableName = "qq_event_log";
    public static final String event_foot = "foot_qzone";
    public static final String event_vip_sign = "qq_vip_sign";
    public static final String event_foot_user_lost_live = "foot_qzone_user_lost_live";

    public static EventLog dao = new EventLog();
}
