package com.github.orekyuu.javatterfx.event.stream;

import twitter4j.DirectMessage;

import com.github.orekyuu.javatterfx.event.Event;

/**
 * ダイレクトメッセージを受け取った時のイベント
 * @author orekyuu
 *
 */
public class EventDirectMessage implements Event{

	private DirectMessage message;
	public EventDirectMessage(DirectMessage arg0) {
		message=arg0;
	}

	public DirectMessage getMessage(){
		return message;
	}

}
