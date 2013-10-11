package com.github.orekyuu.javatterfx.event.stream;

import twitter4j.StatusDeletionNotice;

import com.github.orekyuu.javatterfx.event.Event;

/**
 * つい消し通知イベント
 * @author orekyuu
 *
 */
public class EventTweetRemove implements Event{

	private StatusDeletionNotice status;
	public EventTweetRemove(StatusDeletionNotice arg0){
		status=arg0;
	}

	/**
	 * イベントの情報を得る
	 * @return
	 */
	public StatusDeletionNotice getStatus(){
		return status;
	}
}
