package com.github.orekyuu.javatterfx.event.stream;

import twitter4j.StallWarning;

import com.github.orekyuu.javatterfx.event.Event;

/**
 * よくわからないイベント。<br>
 * 詳しい方教えてください
 * @author orekyuu
 *
 */
public class EventStallWarning implements Event {

	private StallWarning var;
	public EventStallWarning(StallWarning arg0) {
		var=arg0;
	}

	public StallWarning getEventInfo(){
		return var;
	}

}
