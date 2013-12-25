package com.github.orekyuu.javatterfx.controller;

import java.net.URL;
import java.util.HashSet;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.util.Callback;
import twitter4j.Status;

import com.github.orekyuu.javatterfx.event.EventHandler;
import com.github.orekyuu.javatterfx.event.stream.EventLoadHomeTimeline;
import com.github.orekyuu.javatterfx.event.stream.EventStatus;
import com.github.orekyuu.javatterfx.view.TweetListCell;

public class JavatterTimeLineController extends AbstractColumnController implements IStatusList{

	@FXML
	private BorderPane root;
	@FXML
	private Label name;
	@FXML
	private ListView<Status> listView;

	private Map<Long,Parent> map;

	private StatusListAdapter adapter;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		super.initialize(arg0, arg1);
		map=new TreeMap<>();
		adapter=new StatusListAdapter(listView);
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
	 * カラムの上部へ移動
	 * @param event
	 */
	public void onScrollTop(ActionEvent event){
		scrollTop();
	}

	public void onClose(ActionEvent event){
		close(root);
	}

	@EventHandler
	public void onStatus(EventStatus event){
		addStatusTop(event.getStatus());
	}

	@EventHandler
	public void onLoadHomeTimeline(EventLoadHomeTimeline event){
		final Status status=event.getStatus();
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					addStatusLast(status);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	public void addStatusTop(Status status) {
		adapter.addStatusTop(status);
	}

	@Override
	public void addStatusLast(Status status) {
		adapter.addStatusLast(status);
	}

	@Override
	public void removeStatus(Status status) {
		adapter.removeStatus(status);
	}

	@Override
	public void scrollTop() {
		adapter.scrollTop();
	}

}
