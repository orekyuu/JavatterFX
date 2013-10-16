package com.github.orekyuu.javatterfx.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import twitter4j.MediaEntity;
import twitter4j.Status;
import twitter4j.TwitterException;

import com.github.orekyuu.javatterfx.account.TwitterManager;
import com.github.orekyuu.javatterfx.event.EventManager;
import com.github.orekyuu.javatterfx.event.user.EventFavoriteClick;
import com.github.orekyuu.javatterfx.event.user.EventRTClick;
import com.github.orekyuu.javatterfx.event.user.EventReplyClick;
import com.github.orekyuu.javatterfx.util.IconCache;
import com.github.orekyuu.javatterfx.util.TwitterUtil;

public class TweetObjectController implements Initializable,Comparable<TweetObjectController>{

	@FXML
	private BorderPane root;
    @FXML
    private VBox box1;
    @FXML
    private ImageView image;
    @FXML
    private ImageView minimage;
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
    @FXML
    private HBox previewBox;

    private Status status;
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
		image.setImage(IconCache.getInstance().getIcon(url));
	}

	public void setMinImage(String s) throws Exception{
		URL url=new URL(s);
		minimage.setImage(IconCache.getInstance().getIcon(url));
	}

	public void onReply(ActionEvent event){
		EventReplyClick e=new EventReplyClick(status, event);
		EventManager.INSTANCE.eventFire(e);
	}

	public void onFavorite(ActionEvent event){
		EventManager.INSTANCE.eventFire(new EventFavoriteClick(status));
		TwitterUtil.fav(TwitterManager.getInstance().getTwitter(), status);
	}

	public void onRetweet(ActionEvent event){
		try {
			EventManager.INSTANCE.eventFire(new EventRTClick(status));
			TwitterUtil.rt(TwitterManager.getInstance().getTwitter(), status);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}

	public void setStatus(Status status) throws MalformedURLException {
		this.status=status;
		for(MediaEntity entity:status.getMediaEntities()){
			Image img=null;
			try {
				img = IconCache.getInstance().getIcon(new URL(entity.getMediaURL()));
			} catch (IOException e) {
				e.printStackTrace();
			}
			if(img==null)continue;
			ImageView view=new ImageView(img);
			view.setFitHeight(100);
			view.setFitWidth(100);
			previewBox.getChildren().add(view);
		}
	}

	public Status getStatus(){
		return status;
	}

	@Override
	public int compareTo(TweetObjectController o) {
		return (int) (status.getId()-o.getStatus().getId());
	}
}
