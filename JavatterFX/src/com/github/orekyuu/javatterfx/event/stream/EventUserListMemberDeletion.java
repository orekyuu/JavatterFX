package com.github.orekyuu.javatterfx.event.stream;

import twitter4j.User;
import twitter4j.UserList;

import com.github.orekyuu.javatterfx.event.Event;

/**
 * リストメンバー削除イベント
 * @author orekyuu
 *
 */
public class EventUserListMemberDeletion implements Event {

	private User addedMember;
	private User owner;
	private UserList list;
	public EventUserListMemberDeletion(User arg0, User arg1, UserList arg2) {
		addedMember=arg0;
		owner=arg1;
		list=arg2;
	}
	public User getAddedMember() {
		return addedMember;
	}
	public User getOwner() {
		return owner;
	}
	public UserList getList() {
		return list;
	}

}
