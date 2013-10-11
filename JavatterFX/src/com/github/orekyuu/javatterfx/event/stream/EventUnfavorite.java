package com.github.orekyuu.javatterfx.event.stream;

import twitter4j.Status;
import twitter4j.User;

import com.github.orekyuu.javatterfx.event.Event;

/**
 * お気に入り削除イベント
 * @author orekyuu
 *
 */
public class EventUnfavorite implements Event {

	private User source;
	private User target;
	private Status unfavoriteStatus;
	public EventUnfavorite(User arg0, User arg1, Status arg2) {
		source=arg0;
		target=arg1;
		unfavoriteStatus=arg2;
	}
	public User getSource() {
		return source;
	}
	public User getTarget() {
		return target;
	}
	public Status getUnfavoriteStatus() {
		return unfavoriteStatus;
	}

}
