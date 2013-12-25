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
import twitter4j.TwitterException;

import com.github.orekyuu.javatterfx.account.TwitterManager;
import com.github.orekyuu.javatterfx.event.EventHandler;
import com.github.orekyuu.javatterfx.event.stream.EventLoadMensions;
import com.github.orekyuu.javatterfx.event.stream.EventStatus;
import com.github.orekyuu.javatterfx.view.TweetListCell;

public class MensionsController extends AbstractColumnController implements IStatusList{

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

	public void setName(String s){
		name.setText(s);
	}

	public void onScrollTop(ActionEvent event){
		scrollTop();
	}

	public void onClose(ActionEvent event){
		close(root);
	}

	@EventHandler
	public void onStatus(EventStatus event){
		final Status status=event.getStatus();
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					if(isReply(status))
						addStatusTop(status);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private boolean isReply(Status status) throws IllegalStateException, TwitterException{
		String user=TwitterManager.getInstance().getTwitter().getScreenName();
		return user.equals(status.getInReplyToScreenName());
	}

	@EventHandler
	public void onLoadMensions(EventLoadMensions event){
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
	public void removeStatus(Status status) {
		adapter.removeStatus(status);
	}

	@Override
	public void scrollTop() {
		adapter.scrollTop();
	}

	@Override
	public void addStatusTop(Status status) {
		adapter.addStatusTop(status);
	}

	@Override
	public void addStatusLast(Status status) {
		adapter.addStatusLast(status);
	}
}
