package com.github.orekyuu.javatterfx.view;

import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

import javafx.scene.Parent;
import javafx.scene.control.ListCell;
import twitter4j.Status;

import com.github.orekyuu.javatterfx.controller.TweetObjectController;

public class TweetListCell extends ListCell<Status>{

	private static Map<Long,Parent> map=new TreeMap<>();

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

		if(map.containsKey(status.getId())){
			setGraphic(map.get(status.getId()));
			layout();
			return;
		}

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
		c.getRootPane().maxWidthProperty().bind(maxWidthProperty());
		c.getRootPane().maxHeightProperty().bind(maxHeightProperty());
		setGraphic(p);
		map.put(status.getId(), p);
	}
}
