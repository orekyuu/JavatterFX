package com.github.orekyuu.javatterfx.event.stream;

import com.github.orekyuu.javatterfx.event.Event;

import twitter4j.User;
import twitter4j.UserList;

/**
 * UserList作成イベント
 * @author orekyuu
 *
 */
public class EventUserListCreation implements Event{

	private User owner;
	private UserList list;
	public EventUserListCreation(User arg0, UserList arg1) {
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
