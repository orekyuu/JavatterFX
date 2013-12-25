package com.github.orekyuu.javatterfx.controller;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import twitter4j.Status;

public class StatusListAdapter implements IStatusList{

	private ListView<Status> listView;

	private BlockingQueue<Status> tweetQueue = new LinkedBlockingQueue<>();
	private ScrollBar listViewBar;
	private DoubleProperty pos;

	public StatusListAdapter(ListView<Status> listView){
		this.listView=listView;
	}

	/**
	 * スクロールバーの設定
	 */
	private void settingScrollBar(){
		if(listViewBar==null)
			listViewBar=(ScrollBar) listView.lookup(".scroll-bar");
		if(listViewBar==null)
			return;
		if(pos==null)
			pos=listViewBar.valueProperty();

		pos.addListener(new ChangeListener<Number>() {
			@Override
			public void changed(ObservableValue<? extends Number> value, Number oldValue, Number newValue) {
				if(isTop())
					clearTweetQueue();
			}
		});
	}

	private void clearTweetQueue(){
		while(!tweetQueue.isEmpty()){
			try {
				final Status status = tweetQueue.take();
				Platform.runLater(new Runnable() {
					@Override
					public void run() {
						try {
							listView.getItems().add(0, status);
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				});
			} catch (InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}

	@Override
	public void addStatusTop(Status status) {
		settingScrollBar();
		try {
			tweetQueue.put(status);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(isTop())
			clearTweetQueue();
	}

	@Override
	public void addStatusLast(Status status) {
		listView.getItems().add(status);
	}

	@Override
	public void removeStatus(Status status) {
		listView.getItems().remove(status);
	}

	@Override
	public void scrollTop() {
		listView.getSelectionModel().clearAndSelect(0);
		listView.scrollTo(0);
		if(isTop())
			clearTweetQueue();
	}

	private boolean isTop(){
		if(pos==null||tweetQueue.isEmpty())return false;
		return pos.get()==0;
	}
}
