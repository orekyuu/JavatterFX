package com.github.orekyuu.javatterfx.event.user;

import twitter4j.User;

import com.github.orekyuu.javatterfx.event.Event;

/**
 * ユーザーがアイコンをクリックした時のイベント
 * @author kyuuban
 *
 */
public class EventIconClick implements Event{

	private Object source;
	private User user;

	/**
	 *
	 * @param source イベントを発生させたオブジェクト
	 * @param user 表示しているアイコンのユーザー
	 */
	public EventIconClick(Object source,User user){
		this.source=source;
		this.user=user;
	}

	/**
	 * イベント発生させたオブジェクトを返します
	 * @return
	 */
	public Object getSource(){
		return source;
	}

	/**
	 * ユーザーを返します
	 * @return
	 */
	public User getUser(){
		return user;
	}
}
