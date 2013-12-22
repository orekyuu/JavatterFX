package com.github.orekyuu.javatterfx.view;

import java.io.IOException;
import java.util.Map;

import javafx.beans.InvalidationListener;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.GroupBuilder;
import javafx.scene.Parent;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.MenuItem;
import twitter4j.Status;
import twitter4j.URLEntity;

import com.github.orekyuu.javatterfx.controller.TweetObjectController;
import com.github.orekyuu.javatterfx.event.user.EventTweetHyperlinkClick;
import com.github.orekyuu.javatterfx.managers.EventManager;

public class TweetListCell extends ListCell<Status>{

	private Map<Long,Parent> map;

	public TweetListCell(Map<Long,Parent> map){
		this.map=map;
	}

	@Override
	public void updateItem(Status status,boolean flag){
		super.updateItem(status,flag);

		if(flag){
			setText(null);
			setGraphic(null);
			return;
		}

		registerContextMenu(status);
		if(map.containsKey(status.getId())){
			setGraphic(map.get(status.getId()));
			return;
		}

		JavatterFxmlLoader<TweetObjectController> loader=new JavatterFxmlLoader<>();
		@SuppressWarnings("unused")
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

		CellSizeProperty prop=new CellSizeProperty(getListView().widthProperty());
		c.getRootPane().maxWidthProperty().bind(prop);
		c.getRootPane().prefWidthProperty().bind(prop);

		Group group = GroupBuilder.create().children(c.getRootPane()).build();
		setGraphic(group);
		map.put(status.getId(), group);
	}

	private void registerContextMenu(final Status status){
		ContextMenu menu=new ContextMenu();
		for(URLEntity entity:status.getURLEntities()){
			MenuItem item=new MenuItem();
			final String url=entity.getExpandedURL();
			item.setText(url);
			item.setOnAction(new EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent paramT) {
					EventTweetHyperlinkClick event=new EventTweetHyperlinkClick(url, status);
					EventManager.INSTANCE.eventFire(event);
				}
			});
			menu.getItems().add(item);
		}
		setContextMenu(menu);
	}

	class CellSizeProperty extends ReadOnlyDoubleProperty{

		private ReadOnlyDoubleProperty prop;
		public CellSizeProperty(ReadOnlyDoubleProperty prop) {
			this.prop=prop;
		}

		@Override
		public Object getBean() {
			return prop;
		}

		@Override
		public String getName() {
			return prop.getName();
		}

		@Override
		public double get() {
			double space=30;
			return prop.get()-space;
		}

		@Override
		public void addListener(ChangeListener<? super Number> changelistener) {
			prop.addListener(changelistener);
		}

		@Override
		public void removeListener(ChangeListener<? super Number> changelistener) {
			prop.removeListener(changelistener);
		}

		@Override
		public void addListener(InvalidationListener invalidationlistener) {
			prop.addListener(invalidationlistener);
		}

		@Override
		public void removeListener(InvalidationListener invalidationlistener) {
			prop.removeListener(invalidationlistener);
		}

	}
}