package com.github.orekyuu.javatterfx.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import twitter4j.MediaEntity;
import twitter4j.Status;
import twitter4j.TwitterException;

import com.github.orekyuu.javatterfx.account.TwitterManager;
import com.github.orekyuu.javatterfx.event.EventManager;
import com.github.orekyuu.javatterfx.event.user.EventFavoriteClick;
import com.github.orekyuu.javatterfx.event.user.EventIconClick;
import com.github.orekyuu.javatterfx.event.user.EventRTClick;
import com.github.orekyuu.javatterfx.event.user.EventReplyClick;
import com.github.orekyuu.javatterfx.event.user.EventViaClick;
import com.github.orekyuu.javatterfx.util.ImageTask;
import com.github.orekyuu.javatterfx.util.TwitterUtil;

public class TweetObjectController implements Initializable,Comparable<TweetObjectController>{

	@FXML
	private BorderPane root;
	@FXML
	private ImageView image;
	@FXML
	private ImageView minimage;
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

	private String viaURL;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
	}

	public void setAccountName(String s){
		accountname.setText(s);
	}

	public BorderPane getRootPane(){
		return root;
	}

	public void setUserName(String s){
		username.setText(s);
	}

	public void setTweet(String s){
		text.setText(s);
	}

	public Label getLabel(){
		return text;
	}

	public void setVia(String s){
		String regex="https?://[\\w/:%#\\$&\\?\\(\\)~\\.=\\+\\-]+";
		Pattern p=Pattern.compile(regex);
		Matcher matcher=p.matcher(s);
		while (matcher.find()) {
			viaURL=matcher.group();
		}
		via.setText("via "+s.replaceFirst("<a.*\">", "").replace("</a>", ""));
	}

	/**
	 * viaをクリックするとイベントを発生させる
	 * @param event
	 */
	public void OnClickVia(ActionEvent event){
		EventViaClick e=null;
		if(viaURL==null){
			e=new EventViaClick(null, via.getText());
			EventManager.INSTANCE.eventFire(e);
			return;
		}
		try {
			URL url=new URL(viaURL);
			e=new EventViaClick(url, via.getText());
			EventManager.INSTANCE.eventFire(e);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * アイコン画像を設定
	 * @param s
	 * @throws Exception
	 */
	public void setImage(String s) throws Exception{
		ImageTask task=new ImageTask(image,s);
		task.start();
	}

	/**
	 * 小さいアイコン画像を設定
	 * @param s
	 * @throws Exception
	 */
	public void setMinImage(String s) throws Exception{
		ImageTask task=new ImageTask(minimage,s);
		task.start();
	}

	/**
	 * リプライボタンを押したらイベントを発生させる
	 * @param event
	 */
	public void onReply(ActionEvent event){
		EventReplyClick e=new EventReplyClick(status, event);
		EventManager.INSTANCE.eventFire(e);
	}

	/**
	 * ふぁぼを押したらイベントを発生させる
	 * @param event
	 */
	public void onFavorite(ActionEvent event){
		EventManager.INSTANCE.eventFire(new EventFavoriteClick(status));
		TwitterUtil.fav(TwitterManager.getInstance().getTwitter(), status);
	}

	/**
	 * RTを押したらイベントを発生させる
	 * @param event
	 */
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
			ImageView view=new ImageView();
			view.setFitHeight(100);
			view.setFitWidth(100);
			ImageTask task=new ImageTask(view, entity.getMediaURL());
			task.start();
			previewBox.getChildren().add(view);
		}
	}

	public void imageClick(MouseEvent event){
		Status rt=status.getRetweetedStatus();
		if(event.getSource().equals(image)){
			EventIconClick e=new EventIconClick(event.getSource(), rt==null?status.getUser():rt.getUser());
			EventManager.INSTANCE.eventFire(e);
		}else if(event.getSource().equals(minimage)){
			if(rt!=null){
				EventIconClick e=new EventIconClick(event.getSource(), status.getUser());
				EventManager.INSTANCE.eventFire(e);
			}
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
