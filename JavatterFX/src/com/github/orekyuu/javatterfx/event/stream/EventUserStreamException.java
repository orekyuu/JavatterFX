package com.github.orekyuu.javatterfx.event.stream;

import com.github.orekyuu.javatterfx.event.Event;

/**
 * ユーザーストリームにより、例外が発生した時のイベント
 * @author orekyuu
 *
 */
public class EventUserStreamException implements Event {

	private Exception exception;
	public EventUserStreamException(Exception arg0) {
		exception=arg0;
	}

	public Exception getException(){
		return exception;
	}
}
