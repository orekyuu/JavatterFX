package com.github.orekyuu.javatterfx.controller;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;

import javafx.animation.Animation;
import javafx.animation.TranslateTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import twitter4j.User;

import com.github.orekyuu.javatterfx.event.EventManager;
import com.github.orekyuu.javatterfx.event.view.EventUserToolbarCreated;
import com.github.orekyuu.javatterfx.util.ImageTask;
import com.github.orekyuu.javatterfx.view.JavatterFxmlLoader;

public class UserWindowController implements Initializable{

	@FXML
	private Label account;
	@FXML
	private Label name;
	@FXML
	private VBox page;
	@FXML
	private ToolBar bar;
	@FXML
	private ToggleButton userinfo;
	@FXML
	private ImageView icon;
	@FXML
	private Pane pane;

	private Map<ToggleButton,Node> map=new LinkedHashMap<>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	public void setUser(User user){
		account.setText(user.getScreenName());
		name.setText(user.getName());
		ImageTask task=new ImageTask(icon,user.getProfileImageURL());
		task.start();

		ToggleGroup group=new ToggleGroup();
		group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			@Override
			public void changed(ObservableValue<? extends Toggle> observable,
					Toggle oldValue, Toggle newValue) {
				if(newValue==null){
					oldValue.setSelected(true);
					return;
				}

				if(newValue!=null&&oldValue!=null){
					animation(map.get(newValue), pane.getChildren().get(0));
				}
			}
		});
		userinfo.setToggleGroup(group);

		ToggleButton tl=new ToggleButton("TL");
		tl.setToggleGroup(group);
		bar.getItems().add(tl);

		ToggleButton fr=new ToggleButton("Follow");
		fr.setToggleGroup(group);
		bar.getItems().add(fr);

		ToggleButton follower=new ToggleButton("Follower");
		follower.setToggleGroup(group);
		bar.getItems().add(follower);


		try {
			JavatterFxmlLoader<UserInfoTabController> infoTab=new JavatterFxmlLoader<>();
			map.put(userinfo, infoTab.loadFxml("UserInfoTab.fxml"));
			UserInfoTabController c=infoTab.getController();
			c.setUser(user);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			JavatterFxmlLoader<UserTimelineController> timeline=new JavatterFxmlLoader<>();
			map.put(tl, timeline.loadFxml("UserTimeline.fxml"));
			UserTimelineController c=timeline.getController();
			c.load(user);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			JavatterFxmlLoader<FollowTabController> follow=new JavatterFxmlLoader<>();
			map.put(fr, follow.loadFxml("Follow.fxml"));
			FollowTabController c=follow.getController();
			c.load(user);
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
			JavatterFxmlLoader<FollowerTabController> follow=new JavatterFxmlLoader<>();
			map.put(follower, follow.loadFxml("Follower.fxml"));
			FollowerTabController c=follow.getController();
			c.load(user);
		} catch (IOException e) {
			e.printStackTrace();
		}

		userinfo.setSelected(true);
		EventUserToolbarCreated event=new EventUserToolbarCreated(user, bar, group,map);
		EventManager.INSTANCE.eventFire(event);

		pane.getChildren().add(map.get(userinfo));
	}

	private final int MOVETIME=600;
	private final TranslateTransition intrans=new TranslateTransition(Duration.millis(MOVETIME));
	private final TranslateTransition outtrans=new TranslateTransition(Duration.millis(MOVETIME));
	private void animation(Node n,Node o){
		if(intrans.getStatus()==Animation.Status.RUNNING&&
				outtrans.getStatus()==Animation.Status.RUNNING){
			pane.getChildren().clear();
		}

		if(n.equals(o)){
			for(Entry<ToggleButton, Node> set:map.entrySet()){
				if(set.getValue().equals(n)){
					n=set.getValue();
					break;
				}
			}
		}

		pane.getChildren().removeAll(n,o);
		intrans.setNode(n);

		boolean left=isLeft(n, o);
		if(left){
			intrans.setFromX(pane.getScene().getWindow().getWidth());
			intrans.setToX(0);
		}else{
			intrans.setFromX(-pane.getScene().getWindow().getWidth());
			intrans.setToX(0);
		}
		intrans.playFromStart();

		pane.getChildren().add(n);
		pane.getChildren().add(o);

		outtrans.setNode(o);
		outtrans.setOnFinished(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pane.getChildren().remove(outtrans.getNode());
			}
		});
		if(left){
			outtrans.setFromX(0);
			outtrans.setToX(-pane.getScene().getWindow().getWidth());
		}else{
			outtrans.setFromX(0);
			outtrans.setToX(pane.getScene().getWindow().getWidth());
		}
		outtrans.playFromStart();
	}

	private boolean isLeft(Node n,Node o){
		int nIndex=0;
		int oIndex=0;
		int i=0;
		for(Entry<ToggleButton, Node> set:map.entrySet()){
			if(set.getValue().equals(o)){
				oIndex=i;
			}
			if(set.getValue().equals(n)){
				nIndex=i;
			}
			i++;
		}
		return nIndex>oIndex;
	}

}
