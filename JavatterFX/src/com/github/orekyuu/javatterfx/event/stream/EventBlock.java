package com.github.orekyuu.javatterfx.event.stream;

import twitter4j.User;

import com.github.orekyuu.javatterfx.event.Event;

/**
 * ブロックした時のイベント
 * @author orekyuu
 *
 */
public class EventBlock implements Event {

	private User source;
	private User blockedUser;
	public EventBlock(User arg0, User arg1) {
		source=arg0;
		blockedUser=arg1;
	}

	public User getSource(){
		return source;
	}

	public User getBlockedUser(){
		return blockedUser;
	}

}
