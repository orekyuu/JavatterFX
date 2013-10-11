package com.github.orekyuu.javatterfx.event.stream;

import twitter4j.Status;

import com.github.orekyuu.javatterfx.event.Event;

/**
 * ユーザーストリームではない方法によってメンションが読み込まれた時に起きるイベント
 * @author orekyuu
 *
 */
public class EventLoadMensions implements Event{

	private Status status;
	private String userName;
	private String account;
	private String tweetText;
	private long replyToStatusID;
	private long replyToUserID;
	private String replyToScreenName;

	/**
	 * リプライ先のステータスID
	 * @return
	 */
	public long getReplyToStatusID() {
		return replyToStatusID;
	}

	/**
	 * リプライ先のユーザーID
	 * @return
	 */
	public long getReplyToUserID() {
		return replyToUserID;
	}

	/**
	 * リプライ先のユーザー名
	 * @return
	 */
	public String getReplyToScreenName() {
		return replyToScreenName;
	}

	public EventLoadMensions(Status status){
		this.status=status;
		userName=status.getUser().getName();
		account=status.getUser().getScreenName();
		tweetText=status.getText();
		replyToStatusID=status.getInReplyToStatusId();
		replyToUserID=status.getInReplyToUserId();
		replyToScreenName=status.getInReplyToScreenName();
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
