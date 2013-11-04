package com.github.orekyuu.javatterfx.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.VBox;
import twitter4j.PagableResponseList;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.User;

import com.github.orekyuu.javatterfx.account.TwitterManager;
import com.github.orekyuu.javatterfx.view.JavatterFxmlLoader;

public class FollowerTabController implements Initializable{

	@FXML
	private VBox box;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	public void load(User user){
		try {
			Twitter twitter=TwitterManager.getInstance().getTwitter();
			PagableResponseList<User> friends=twitter.getFollowersList(user.getId(), -1);
			for(User u:friends){
				box.getChildren().add(getObject(u));
			}
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}

	private Parent getObject(User user){
		JavatterFxmlLoader<UserObjectController> loader=new JavatterFxmlLoader<>();
		Parent p=null;
		try {
			p = loader.loadFxml("UserObject.fxml");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		loader.getController().setUser(user);
		return p;
	}
}
