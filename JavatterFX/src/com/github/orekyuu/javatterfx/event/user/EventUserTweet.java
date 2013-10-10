package com.github.orekyuu.javatterfx.event.user;

import twitter4j.User;

import com.github.orekyuu.javatterfx.event.Event;

public class EventUserTweet implements Event {

	private String text;
	private User user;
	private EventType type;
	public EventUserTweet(String text,User user,EventType type){
		this.text=text;
		this.user=user;
		this.type=type;
	}

	public String getText(){
		return text;
	}

	public void setText(String text){
		this.text=text;
	}

	public User getUser(){
		return user;
	}

	public EventType getEventType(){
		return type;
	}

	public enum EventType{
		SHORTCUT,
		BUTTON;
	}
}
