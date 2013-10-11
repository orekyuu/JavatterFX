package com.github.orekyuu.javatterfx.event.stream;

import twitter4j.User;

import com.github.orekyuu.javatterfx.event.Event;

/**
 * フォローイベント
 * @author orekyuu
 *
 */
public class EventFollow implements Event {

	private User source;
	private User followedUser;
	public EventFollow(User arg0, User arg1) {
		source=arg0;
		followedUser=arg1;
	}
	public User getSource() {
		return source;
	}
	public User getFollowedUser() {
		return followedUser;
	}

}
