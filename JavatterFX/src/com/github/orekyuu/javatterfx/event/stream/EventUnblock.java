package com.github.orekyuu.javatterfx.event.stream;

import twitter4j.User;

import com.github.orekyuu.javatterfx.event.Event;

/**
 * ブロック解除イベント
 * @author orekyuu
 *
 */
public class EventUnblock implements Event {
	private User source;
	private User unblockedUser;
	public EventUnblock(User arg0, User arg1) {
		source=arg0;
		unblockedUser=arg1;
	}
	public User getSource() {
		return source;
	}
	public User getUnblockedUser() {
		return unblockedUser;
	}

}
