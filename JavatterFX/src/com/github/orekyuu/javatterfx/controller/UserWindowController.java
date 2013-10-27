package com.github.orekyuu.javatterfx.controller;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

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
import com.github.orekyuu.javatterfx.util.IconCache;

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

	private Map<ToggleButton,Node> map=new HashMap<>();

	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	public void setUser(User user){
		account.setText(user.getScreenName());
		name.setText(user.getName());
		try {
			icon.setImage(IconCache.getInstance().getIcon(new URL(user.getProfileImageURL())));
		} catch (IOException e) {
			e.printStackTrace();
		}

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

		ToggleButton tl=new ToggleButton("tl");
		tl.setToggleGroup(group);
		bar.getItems().add(tl);

		map.put(userinfo, new Label("User"));
		map.put(tl, new Label("TL"));

		userinfo.setSelected(true);
		EventUserToolbarCreated event=new EventUserToolbarCreated(user, bar, group,map);
		EventManager.INSTANCE.eventFire(event);

		pane.getChildren().add(map.get(userinfo));
	}

	private void animation(Node n,Node o){
		TranslateTransition intrans=new TranslateTransition(Duration.millis(300),n);
		intrans.setFromX(pane.getScene().getWindow().getWidth());
		intrans.setToX(0);
		intrans.play();
		pane.getChildren().add(n);

		TranslateTransition outtrans=new TranslateTransition(Duration.millis(300),o);
		outtrans.setOnFinished(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent event) {
				pane.getChildren().remove(0);
			}
		});
		outtrans.setFromX(0);
		outtrans.setToX(-pane.getScene().getWindow().getWidth());
		outtrans.play();
	}

}
