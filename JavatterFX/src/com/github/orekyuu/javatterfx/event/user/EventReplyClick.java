package com.github.orekyuu.javatterfx.event.user;

import javafx.event.ActionEvent;
import twitter4j.Status;

import com.github.orekyuu.javatterfx.event.Event;

public class EventReplyClick implements Event {

	private Status status;
	private ActionEvent event;
	public EventReplyClick(Status status,ActionEvent event){
		this.status=status;
		this.event=event;
	}
	public Status getStatus() {
		return status;
	}
	public ActionEvent getEvent() {
		return event;
	}
}
