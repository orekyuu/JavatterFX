package com.github.orekyuu.javatterfx.event.stream;

import twitter4j.Status;

import com.github.orekyuu.javatterfx.event.Event;

public class EventStatus implements Event{

	private Status status;
	private String userName;
	private String account;
	private String tweetText;

	public EventStatus(Status status){
		this.status=status;
		userName=status.getUser().getName();
		account=status.getUser().getScreenName();
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
