package com.github.orekyuu.javatterfx.event.user;

import twitter4j.Status;

import com.github.orekyuu.javatterfx.event.Event;

/**
 * お気に入り登録ボタンを押した時のイベント
 * @author orekyuuPC
 *
 */
public class EventFavoriteClick implements Event{

	private Status status;
	public EventFavoriteClick(Status status){
		this.status=status;
	}

	/**
	 * RT対象のStatusを得る
	 * @return
	 */
	public Status getStatus(){
		return status;
	}
}
