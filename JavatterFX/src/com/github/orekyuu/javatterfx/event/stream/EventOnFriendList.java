package com.github.orekyuu.javatterfx.event.stream;

import java.util.Arrays;

import com.github.orekyuu.javatterfx.event.Event;

public class EventOnFriendList implements Event {

	private long[] friendIds;
	public EventOnFriendList(long[] arg0) {
		friendIds=arg0;
	}
	public long[] getFriendIds() {
		return Arrays.copyOf(friendIds, friendIds.length);
	}

}
