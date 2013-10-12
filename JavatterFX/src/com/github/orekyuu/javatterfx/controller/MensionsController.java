package com.github.orekyuu.javatterfx.controller;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import twitter4j.Status;
import twitter4j.TwitterException;

import com.github.orekyuu.javatterfx.account.TwitterManager;
import com.github.orekyuu.javatterfx.event.EventHandler;
import com.github.orekyuu.javatterfx.event.stream.EventLoadMensions;
import com.github.orekyuu.javatterfx.event.stream.EventStatus;
import com.github.orekyuu.javatterfx.view.JavatterFxmlLoader;

public class MensionsController extends AbstractColumnController{

	@FXML
	private BorderPane root;

	@FXML
	private ToolBar bar;

	@FXML
	private Label name;

	@FXML
	private VBox box;

	@FXML
	private ScrollPane scroll;

	public void setName(String s){
		name.setText(s);
	}

	public void addObject(Parent p) {
		box.getChildren().add(0, p);
	}
	public void addLast(Parent p) {
		box.getChildren().add(p);
	}

	public void onScrollTop(ActionEvent event){
		scroll.setVvalue(0);
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
					addObject(getObject(status));
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
					addLast(getObject(status));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	private Parent getObject(Status status){
		JavatterFxmlLoader<TweetObjectController> loader=new JavatterFxmlLoader<>();
		Parent p=null;
		try {
			p = loader.loadFxml("TweetObject.fxml");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		TweetObjectController c=loader.getController();
		try {
			if(!status.isRetweet()){
				c.setAccountName("@"+status.getUser().getScreenName());
				c.setUserName(status.getUser().getName());
				c.setVia(status.getSource());
				c.setTweet(status.getText());
				c.setStatus(status);
				c.setImage(status.getUser().getProfileImageURL());
			}else{
				c.setAccountName("@"+status.getRetweetedStatus().getUser().getScreenName());
				c.setUserName(status.getRetweetedStatus().getUser().getName());
				c.setVia(status.getRetweetedStatus().getSource());
				c.setTweet(status.getRetweetedStatus().getText());
				c.setStatus(status);
				c.setImage(status.getRetweetedStatus().getUser().getProfileImageURL());
				c.setMinImage(status.getUser().getProfileImageURL());
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return p;
	}
}
