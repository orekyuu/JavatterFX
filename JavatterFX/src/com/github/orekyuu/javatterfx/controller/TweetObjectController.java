package com.github.orekyuu.javatterfx.controller;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class TweetObjectController implements Initializable{

	@FXML
	private BorderPane root;
    @FXML
    private VBox box1;
    @FXML
    private ImageView image;
    @FXML
	private BorderPane border;
    @FXML
    private HBox box2;
    @FXML
    private Label accountname;
    @FXML
    private Label username;
    @FXML
    private Label text;
    @FXML
    private Hyperlink via;

	@Override
	public void initialize(URL location, ResourceBundle resources) {

	}

	public void setAccountName(String s){
		accountname.setText(s);
	}

	public void setUserName(String s){
		username.setText(s);
	}

	public void setTweet(String s){
		text.setText(s);
	}

	public void setVia(String s){
		via.setText("via "+s.replaceFirst("<a.*\">", "").replace("</a>", ""));
	}

	public void setImage(String s) throws Exception{
		URL url=new URL(s);
		HttpURLConnection http=(HttpURLConnection) url.openConnection();
		http.setRequestMethod("GET");
		http.connect();
		image.setImage(new Image(http.getInputStream()));
		http.disconnect();
	}
}
