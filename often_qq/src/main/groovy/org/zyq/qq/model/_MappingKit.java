package org.zyq.qq.model;

import com.jfinal.plugin.activerecord.ActiveRecordPlugin;

/**
 * Generated by JFinal, do not modify this file.
 * <pre>
 * Example:
 * public void configPlugin(Plugins me) {
 *     ActiveRecordPlugin arp = new ActiveRecordPlugin(...);
 *     _MappingKit.mapping(arp);
 *     me.add(arp);
 * }
 * </pre>
 */
public class _MappingKit {

	public static void mapping(ActiveRecordPlugin arp) {
		arp.addMapping("qq_cookie", "id", Cookie.class);
		arp.addMapping("qq_event_log", "id", EventLog.class);
		arp.addMapping("qq_service", "id", Service.class);
		arp.addMapping("qq_user", "id", User.class);
	}
}

