package com.github.orekyuu.javatterfx.event.stream;

import twitter4j.Status;

import com.github.orekyuu.javatterfx.event.Event;

public class EventLoadMensions implements Event{

	private Status status;
	private String userName;
	private String account;
	private String tweetText;
	private long replyToStatusID;
	private long replyToUserID;
	private String replyToScreenName;

	public long getReplyToStatusID() {
		return replyToStatusID;
	}

	public long getReplyToUserID() {
		return replyToUserID;
	}

	public String getReplyToScreenName() {
		return replyToScreenName;
	}

	public EventLoadMensions(Status status){
		this.status=status;
		userName=status.getUser().getName();
		account=status.getUser().getScreenName();
		replyToStatusID=status.getInReplyToStatusId();
		replyToUserID=status.getInReplyToUserId();
		replyToScreenName=status.getInReplyToScreenName();
	}

	public Status getStatus() {
		return status;
	}
	public String getUserName() {
		return userName;
	}
	public String getAccount() {
		return account;
	}
	public String getTweetText() {
		return tweetText;
	}
}
