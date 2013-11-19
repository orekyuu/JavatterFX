package com.github.orekyuu.javatterfx.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
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

public class MensionsController extends AbstractColumnController{

	@FXML
	private BorderPane root;

	@FXML
	private Label name;

	@FXML
	private ListView<Status> box;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		super.initialize(arg0, arg1);
		box.setCellFactory(new Callback<ListView<Status>, ListCell<Status>>() {

			@Override
			public ListCell<Status> call(ListView<Status> param) {
				TweetListCell cell= new TweetListCell();
				cell.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE);
				return cell;
			}
		});
	}

	public void setName(String s){
		name.setText(s);
	}

	public void addObject(Status p) {
		box.getItems().add(0, p);
	}
	public void addLast(Status p) {
		box.getItems().add(p);
	}

	public void onScrollTop(ActionEvent event){
		box.getSelectionModel().clearAndSelect(0);
		box.scrollTo(0);
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
					addObject(status);
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
					addLast(status);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
