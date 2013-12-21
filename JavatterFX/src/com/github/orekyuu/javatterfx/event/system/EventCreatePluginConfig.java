package com.github.orekyuu.javatterfx.event.system;

import javafx.scene.control.MenuItem;

import com.github.orekyuu.javatterfx.event.Event;

/**
 * プラグインコンフィグのMenuItemが作成された時のイベント
 * @author kyuuban
 *
 */
public class EventCreatePluginConfig implements Event{

	private MenuItem item;
	public EventCreatePluginConfig(MenuItem item){
		this.item=item;
	}

	/**
	 * 作成されたMenuItem
	 * @return
	 */
	public MenuItem getItem(){
		return item;
	}
}
