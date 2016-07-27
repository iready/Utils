package org.zyq.qq.threadGroup

import org.zyq.qq.SysVar
import org.zyq.qq.model.EventLog
import org.zyq.qq.service.Qq_eventService

class ZoneBrush implements Runnable {
    void run() {
        ['981471846'].each {
            SysVar.qzoneExecutor.execute(new Runnable() {
                void run() {
                    Qq_eventService.server.find_time_or_not_esixt(EventLog.event_foot, 10, it,"MINUTE").each {
                        iv ->
                            if (SysVar.activeUser.containsKey(iv.userId)) {
                                SysVar.zoneUtils.footFreindZone(SysVar.activeUser.get(iv.userId), it);
                            }
                    }
                }
            })
        }
    }
}