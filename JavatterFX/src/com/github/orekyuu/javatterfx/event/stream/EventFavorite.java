package com.github.orekyuu.javatterfx.event.stream;

import twitter4j.Status;
import twitter4j.User;

import com.github.orekyuu.javatterfx.event.Event;

/**
 * ふぁぼイベント
 * @author orekyuu
 *
 */
public class EventFavorite implements Event {

	private User source;
	private User target;
	private Status status;
	public EventFavorite(User arg0, User arg1, Status arg2) {
		source=arg0;
		target=arg1;
		status=arg2;
	}
	public User getSource() {
		return source;
	}
	public User getTarget() {
		return target;
	}
	public Status getStatus() {
		return status;
	}
}
