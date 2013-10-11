package com.github.orekyuu.javatterfx.event.system;

import com.github.orekyuu.javatterfx.event.Event;

public class EventPluginLoad implements Event{

	private String name;
	private String version;
	public EventPluginLoad(String name,String version){
		this.name=name;
		this.version=version;
	}
	public String getName() {
		return name;
	}
	public String getVersion() {
		return version;
	}
}
