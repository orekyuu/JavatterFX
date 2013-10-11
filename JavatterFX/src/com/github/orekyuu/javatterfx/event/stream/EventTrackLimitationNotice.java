package com.github.orekyuu.javatterfx.event.stream;

import com.github.orekyuu.javatterfx.event.Event;

/**
 * よくわからないイベント。<br>
 * 詳しい方教えてください
 * @author orekyuu
 *
 */
public class EventTrackLimitationNotice implements Event {

	private int var;
	public EventTrackLimitationNotice(int arg0) {
		var=arg0;
	}

	public int getLimitStatus(){
		return var;
	}

}
