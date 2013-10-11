package com.github.orekyuu.javatterfx.event.stream;

import twitter4j.User;
import twitter4j.UserList;

import com.github.orekyuu.javatterfx.event.Event;

public class EventUserListUnscription implements Event{

	private User subscriber;
	private User owner;
	private UserList list;
	public EventUserListUnscription(User arg0, User arg1, UserList arg2) {
		subscriber=arg0;
		owner=arg1;
		list=arg2;
	}
	public User getSubscriber() {
		return subscriber;
	}
	public User getOwner() {
		return owner;
	}
	public UserList getList() {
		return list;
	}

}
