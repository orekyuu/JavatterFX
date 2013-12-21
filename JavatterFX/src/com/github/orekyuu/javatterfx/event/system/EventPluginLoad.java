package com.github.orekyuu.javatterfx.event.system;

import com.github.orekyuu.javatterfx.event.Event;

/**
 * プラグインがロードされた時のイベント
 * @author kyuuban
 *
 */
public class EventPluginLoad implements Event{

	private String name;
	private String version;
	private Object plugin;
	public EventPluginLoad(String name,String version,Object plugin){
		this.name=name;
		this.version=version;
		this.plugin=plugin;
	}
	/**
	 * プラグインの名前
	 * @return
	 */
	public String getName() {
		return name;
	}
	/**
	 * プラグインのバージョン
	 * @return
	 */
	public String getVersion() {
		return version;
	}
	/**
	 * プラグインオブジェクト
	 * @return
	 */
	public Object getPlugin() {
		return plugin;
	}
}
