package com.github.orekyuu.javatterfx.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.User;

import com.github.orekyuu.javatterfx.account.TwitterManager;
import com.github.orekyuu.javatterfx.view.JavatterFxmlLoader;

public class UserTimelineController implements Initializable{

	@FXML
	private VBox box;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	public void load(User user){
		try {
			ResponseList<Status> list=TwitterManager.getInstance().getTwitter().getUserTimeline(user.getId(),new Paging());
			for(Status s : list){
				box.getChildren().add(getObject(s));
			}
		} catch (TwitterException e) {
			e.printStackTrace();
		}
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
				c.setStatus(status);
				c.setImage(status.getUser().getProfileImageURL());
			}else{
				c.setAccountName("@"+status.getRetweetedStatus().getUser().getScreenName());
				c.setUserName(status.getRetweetedStatus().getUser().getName());
				c.setVia(status.getRetweetedStatus().getSource());
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
