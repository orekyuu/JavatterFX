package com.github.orekyuu.javatterfx.event.user;

import twitter4j.Status;

import com.github.orekyuu.javatterfx.event.Event;

/**
 * ツイート本文のハイパーリンクをクリックした時のイベント
 * @author kyuuban
 *
 */
public class EventTweetHyperlinkClick implements Event{

	private String link;
	private Status status;
	public EventTweetHyperlinkClick(String link,Status status){
		this.link=link;
		this.status=status;
	}

	/**
	 * ハイパーリンクのURLを返す
	 * @return
	 */
	public String getLinkURL(){
		return link;
	}

	/**
	 * ハイパーリンクが含まれるツイートのStatusを返す
	 * @return
	 */
	public Status getTweetStatus(){
		return status;
	}
}
