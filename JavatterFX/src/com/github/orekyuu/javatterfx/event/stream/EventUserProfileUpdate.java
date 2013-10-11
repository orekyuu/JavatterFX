package com.github.orekyuu.javatterfx.event.stream;

import twitter4j.User;

import com.github.orekyuu.javatterfx.event.Event;

/**
 * プロフィール更新イベント
 * @author orekyuu
 *
 */
public class EventUserProfileUpdate implements Event {

	private User user;
	public EventUserProfileUpdate(User arg0) {
		user=arg0;
	}
	public User getUser() {
		return user;
	}

}
