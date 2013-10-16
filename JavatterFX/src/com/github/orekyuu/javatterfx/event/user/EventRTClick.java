package com.github.orekyuu.javatterfx.event.user;

import twitter4j.Status;

import com.github.orekyuu.javatterfx.event.Event;

/**
 * RTボタンを押した時のイベント
 * @author orekyuuPC
 */
public class EventRTClick implements Event{

	private Status status;
	public EventRTClick(Status status){
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
