package com.github.orekyuu.javatterfx.controller;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import twitter4j.MediaEntity;
import twitter4j.Status;
import twitter4j.TwitterException;
import twitter4j.URLEntity;

import com.github.orekyuu.javatterfx.account.TwitterManager;
import com.github.orekyuu.javatterfx.event.user.EventFavoriteClick;
import com.github.orekyuu.javatterfx.event.user.EventIconClick;
import com.github.orekyuu.javatterfx.event.user.EventRTClick;
import com.github.orekyuu.javatterfx.event.user.EventReplyClick;
import com.github.orekyuu.javatterfx.event.user.EventTweetHyperlinkClick;
import com.github.orekyuu.javatterfx.event.user.EventViaClick;
import com.github.orekyuu.javatterfx.event.view.EventInitializeTweetobject;
import com.github.orekyuu.javatterfx.managers.EventManager;
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
	private FlowPane tweetPane;
	@FXML
	private Hyperlink via;
	@FXML
	private HBox previewBox;
	@FXML
	private MenuButton menu;
	@FXML
	private Button rtButton;
	@FXML
	private ToggleButton favButton;
	@FXML
	private Label timeLabel;

	private Status status;

	private String viaURL;
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		EventInitializeTweetobject event=new EventInitializeTweetobject(this);
		EventManager.INSTANCE.eventFire(event);
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
	 * メニューボタンにアイテムを追加
	 * @param button
	 */
	public void addMenuItem(MenuItem button){
		menu.getItems().add(button);
	}

	public FlowPane getTextPane(){
		return tweetPane;
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
		if(favButton.isSelected()){
			EventManager.INSTANCE.eventFire(new EventFavoriteClick(status));
			TwitterUtil.fav(TwitterManager.getInstance().getTwitter(), status);
			return;
		}
		TwitterUtil.unfav(TwitterManager.getInstance().getTwitter(), status);
	}

	/**
	 * RTを押したらイベントを発生させる
	 * @param event
	 */
	public void onRetweet(ActionEvent event){
		try {
			((Button) event.getSource()).setDisable(true);
			EventManager.INSTANCE.eventFire(new EventRTClick(status));
			TwitterUtil.rt(TwitterManager.getInstance().getTwitter(), status);
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}

	public void setStatus(Status status) throws MalformedURLException {
		this.status=status;
		Status s=status;
		if(status.isRetweet()){
			s=status.getRetweetedStatus();
		}
		rtButton.setDisable(s.isRetweetedByMe());
		favButton.setSelected(s.isFavorited());
		setTweetImage();
		setTweetText(s);

		Date d=s.getCreatedAt();
		String date=new SimpleDateFormat("yyyy/MM/dd hh:mm:ss").format(d);
		timeLabel.setText(date);
	}

	/**
	 * ツイートの本文を設定
	 * @param status
	 */
	private void setTweetText(Status status){
		String text=status.getText();
		for(URLEntity entity:status.getURLEntities()){
			text=text.replace(entity.getURL(), "\0"+entity.getExpandedURL()+"\0");
		}

		final Status tweet=this.status;
		for(String s:text.split("\0")){
			final URLEntity entity=getURLEntity(s, status);
			if(entity!=null){
				Hyperlink link=new Hyperlink();
				link.setText(s);
				link.setOnAction(new EventHandler<ActionEvent>() {

					@Override
					public void handle(ActionEvent event) {
						EventManager.INSTANCE.eventFire(new EventTweetHyperlinkClick(entity.getExpandedURL(), tweet));
					}

				});
				tweetPane.getChildren().add(link);
			}else{
				tweetPane.getChildren().add(new Label(s));
			}
		}
	}

	private URLEntity getURLEntity(String text,Status status){
		for(URLEntity entity:status.getURLEntities()){
			if(entity.getExpandedURL().equals(text))return entity;
		}
		return null;
	}

	/**
	 * ツイートに添付された画像を表示
	 */
	private void setTweetImage(){
		for(MediaEntity entity:status.getMediaEntities()){
			final ImageView view=new ImageView();
			view.setFitHeight(100);
			view.setFitWidth(100);
			ImageTask task=new ImageTask(view, entity.getMediaURL());
			task.start();
			previewBox.getChildren().add(view);
			view.setOnMouseClicked(new EventHandler<Event>() {

				@Override
				public void handle(Event event) {
					Image image=view.getImage();
					final Stage stage=new Stage(StageStyle.TRANSPARENT);
					Group rootGroup=new Group();
					Scene scene = new Scene(rootGroup, image.getWidth(), image.getHeight(), Color.TRANSPARENT);
					stage.setScene(scene);
					stage.centerOnScreen();
					stage.show();

					ImageView img=new ImageView(image);
					rootGroup.getChildren().add(img);
					rootGroup.setOnMouseClicked(new EventHandler<Event>() {

						@Override
						public void handle(Event event) {
							stage.close();
						}
					});
				}
			});
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
