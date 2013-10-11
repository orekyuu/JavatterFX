package com.github.orekyuu.javatterfx.event.stream;

import twitter4j.Status;

import com.github.orekyuu.javatterfx.event.Event;

/**
 * ユーザーストリームによる新しいツイートの通知イベント
 * @author orekyuu
 *
 */
public class EventStatus implements Event{

	private Status status;
	private String userName;
	private String account;
	private String tweetText;

	public EventStatus(Status status){
		this.status=status;
		userName=status.getUser().getName();
		account=status.getUser().getScreenName();
		tweetText=status.getText();
	}

	/**
	 * 通知されたStatus
	 * @return
	 */
	public Status getStatus() {
		return status;
	}
	/**
	 * ユーザー名
	 * @return
	 */
	public String getUserName() {
		return userName;
	}
	/**
	 * アカウント名
	 * @return
	 */
	public String getAccount() {
		return account;
	}
	/**
	 * ツイートの文
	 * @return
	 */
	public String getTweetText() {
		return tweetText;
	}
}
