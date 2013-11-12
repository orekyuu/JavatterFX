package com.github.orekyuu.javatterfx.view;

import java.io.IOException;

import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import twitter4j.Status;

import com.github.orekyuu.javatterfx.controller.TweetObjectController;

public class TweetListCell extends ListCell<Status>{

	private ListView<Status> parent;
	public TweetListCell(ListView<Status> view){
		parent=view;
	}

	@Override
	public void updateItem(Status status,boolean flag){
		super.updateItem(status,flag);
		if(flag){
			setText(null);
			setGraphic(null);
			return;
		}

		if(getGraphic()!=null)return;

		setText(null);
		setGraphic(null);
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

		c.getLabel().maxWidthProperty().bind(parent.widthProperty());
		c.getRootPane().maxHeightProperty().bind(maxHeightProperty());
		setGraphic(p);
	}
}
