package com.github.orekyuu.javatterfx.event.stream;

import twitter4j.User;
import twitter4j.UserList;

import com.github.orekyuu.javatterfx.event.Event;

/**
 * ユーザーリスト更新イベント
 * @author orekyuu
 *
 */
public class EventUserListUpdate implements Event {

	private User owner;
	private UserList list;
	public EventUserListUpdate(User arg0, UserList arg1) {
		owner=arg0;
		list=arg1;
	}
	public User getOwner() {
		return owner;
	}
	public UserList getList() {
		return list;
	}

}
