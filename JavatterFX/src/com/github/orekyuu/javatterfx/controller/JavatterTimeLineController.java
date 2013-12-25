package com.github.orekyuu.javatterfx.controller;

import java.net.URL;
import java.util.HashSet;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javafx.application.Platform;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import twitter4j.Status;

import com.github.orekyuu.javatterfx.event.EventHandler;
import com.github.orekyuu.javatterfx.event.stream.EventLoadHomeTimeline;
import com.github.orekyuu.javatterfx.event.stream.EventStatus;
import com.github.orekyuu.javatterfx.view.TweetListCell;

public class JavatterTimeLineController extends AbstractColumnController{

	@FXML
	private BorderPane root;
	@FXML
	private Label name;
	@FXML
	private ListView<Status> listView;

	private Map<Long,Parent> map;

	private BlockingQueue<Status> tweetQueue = new LinkedBlockingQueue<>();
	private ScrollBar listViewBar;
	private DoubleProperty pos;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		super.initialize(arg0, arg1);
		map=new TreeMap<>();
		listView.setItems(FXCollections.observableArrayList(new HashSet<Status>()));
		listView.setCellFactory(new Callback<ListView<Status>, ListCell<Status>>() {

			@Override
			public ListCell<Status> call(ListView<Status> param) {
				TweetListCell cell= new TweetListCell(map);
				return cell;
			}
		});
	}

	/**
	 * カラムの名前を設定する
	 * @param s
	 */
	public void setName(String s){
		name.setText(s);
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

	private void addObject(Status p) {
		settingScrollBar();
		try {
			tweetQueue.put(p);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		if(isTop())
			clearTweetQueue();
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

	private void addLast(Status p) {
		listView.getItems().add(p);
	}

	/**
	 * カラムの上部へ移動
	 * @param event
	 */
	public void onScrollTop(ActionEvent event){
		listView.getSelectionModel().clearAndSelect(0);
		listView.scrollTo(0);
		if(isTop())
			clearTweetQueue();
	}

	public void onClose(ActionEvent event){
		close(root);
	}

	private boolean isTop(){
		if(pos==null||tweetQueue.isEmpty())return false;
		return pos.get()==0;
	}

	@EventHandler
	public void onStatus(EventStatus event){
		addObject(event.getStatus());
	}

	@EventHandler
	public void onLoadHomeTimeline(EventLoadHomeTimeline event){
		final Status status=event.getStatus();
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					addLast(status);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

}
