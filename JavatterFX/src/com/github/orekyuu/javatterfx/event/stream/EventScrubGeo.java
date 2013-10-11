package com.github.orekyuu.javatterfx.event.stream;

import com.github.orekyuu.javatterfx.event.Event;

/**
 * ツイートの位置情報削除イベント
 * @author orekyuu
 *
 */
public class EventScrubGeo implements Event {

	private long userId;
	private long upToStatusId;
	public EventScrubGeo(long userId,long upToStatusId){
		this.userId=userId;
		this.upToStatusId=upToStatusId;
	}
	/**
	 * ユーザーID
	 * @return
	 */
	public long getUserId() {
		return userId;
	}
	/**
	 * ステータスのID
	 * @return
	 */
	public long getUpToStatusId() {
		return upToStatusId;
	}
}
