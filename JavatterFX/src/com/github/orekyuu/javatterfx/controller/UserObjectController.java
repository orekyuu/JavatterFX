package com.github.orekyuu.javatterfx.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import twitter4j.User;

import com.github.orekyuu.javatterfx.event.EventManager;
import com.github.orekyuu.javatterfx.event.user.EventIconClick;
import com.github.orekyuu.javatterfx.util.IconCache;

public class UserObjectController implements Initializable{

	@FXML
	private ImageView image;
	@FXML
	private Label account;
	@FXML
	private Label name;
	@FXML
	private Label message;

	private User user;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public void setUser(User user){
		this.user=user;
		try {
			URL url = new URL(user.getProfileImageURL());
			image.setImage(IconCache.getInstance().getIcon(url));
		} catch (IOException e) {
			e.printStackTrace();
		}
		account.setText(user.getScreenName());
		name.setText(user.getName());
		message.setText(user.getDescription());
	}

	public void imageClick(MouseEvent event){
		if(event.getSource().equals(image)){
			EventIconClick e=new EventIconClick(event.getSource(), user);
			EventManager.INSTANCE.eventFire(e);
		}
	}

}
