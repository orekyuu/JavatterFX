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

public class JavatterTimeLineController extends AbstractColumnController{

	@FXML
	private BorderPane root;

	@FXML
	private Label name;

	@FXML
	private ListView<Status> box;

	private Map<Long,Parent> map;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		super.initialize(arg0, arg1);
		map=new TreeMap<>();
		box.setItems(FXCollections.observableArrayList(new HashSet<Status>()));
		box.setCellFactory(new Callback<ListView<Status>, ListCell<Status>>() {

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
					addObject(status);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
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
