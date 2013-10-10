package com.github.orekyuu.javatterfx.controller;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import twitter4j.Status;
import twitter4j.TwitterException;

import com.github.orekyuu.javatterfx.account.TwitterManager;
import com.github.orekyuu.javatterfx.event.EventHandler;
import com.github.orekyuu.javatterfx.event.EventManager;
import com.github.orekyuu.javatterfx.event.Listener;
import com.github.orekyuu.javatterfx.event.stream.EventStatus;
import com.github.orekyuu.javatterfx.event.user.EventUserTweet;
import com.github.orekyuu.javatterfx.event.user.EventUserTweet.EventType;
import com.github.orekyuu.javatterfx.main.Main;
import com.github.orekyuu.javatterfx.util.TwitterUtil;
import com.github.orekyuu.javatterfx.view.JavatterFxmlLoader;


public class WindowController implements Initializable, Listener{

	@FXML
	private BorderPane root;
    @FXML
    private BorderPane topborder;
    @FXML
    private TextArea tweet;
    @FXML
    private ToolBar bar;
    @FXML
    private Button config;
    @FXML
    private MenuButton plugin;
    @FXML
    private Button javabeam;
    @FXML
    private Button tweetbutton;
    @FXML
    private ScrollPane scroll;
    @FXML
    private HBox box;

    private JavatterLineController timelinecontroller;

    private JavatterLineController replycontroller;

    /**
     * 初期化処理
     */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		try {
			{
				JavatterFxmlLoader<JavatterLineController> loader=new JavatterFxmlLoader<>();
				Parent p=loader.loadFxml("JavatterLine.fxml");
				timelinecontroller=loader.getController();
				timelinecontroller.setName("TimeLine");
				addChilden(p);
				if(Main.DEBUG){
					createObject(timelinecontroller, JavatterFxmlLoader.load("TweetObject.fxml"));
				}
			}
			{
				JavatterFxmlLoader<JavatterLineController> loader=new JavatterFxmlLoader<>();
				Parent p=loader.loadFxml("JavatterLine.fxml");
				replycontroller=loader.getController();
				replycontroller.setName("Reply");
				addChilden(p);
				if(Main.DEBUG){
					createObject(replycontroller, JavatterFxmlLoader.load("TweetObject.fxml"));
				}
			}
			EventManager.INSTANCE.addEventListener(this);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * コンポーネントを追加
	 * @param node
	 */
	public void addChilden(Node node){
		box.getChildren().add(node);
	}

	@EventHandler
	public void onStatus(EventStatus event){
		final Status status=event.getStatus();
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					if(isReply(status)){
						createObject(replycontroller, getObject(status));
					}
					createObject(timelinecontroller,getObject(status));
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	@FXML
    public void onJavaBeam(ActionEvent event) {
		javaBeam();
	}

	@FXML
    public void onTweet(ActionEvent event) {
		tweet(EventType.BUTTON);
	}

	/**
	 * ショートカットキーでツイート
	 * @param event
	 */
	public void onChangeText(KeyEvent event){
		if(event.isShiftDown()||event.isControlDown()){
			if(KeyCode.ENTER==event.getCode()){
				tweet(EventType.SHORTCUT);
				event.consume();
			}
			if(KeyCode.J==event.getCode()){
				javaBeam();
				event.consume();
			}
		}
	}

	private void javaBeam() {
		TwitterUtil util=new TwitterUtil();
		try {
			util.tweet(TwitterManager.getInstance().getTwitter(), "JavaFXビームﾋﾞﾋﾞﾋﾞﾋﾞﾋﾞﾋﾞwwwwwwww");
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}

	private void tweet(EventType type){
		TwitterUtil util=new TwitterUtil();
		try {
			EventUserTweet event=new EventUserTweet(tweet.getText(),TwitterManager.getInstance().getUser(),type);
			EventManager.INSTANCE.eventFire(event);
			util.tweet(TwitterManager.getInstance().getTwitter(), event.getText());
		} catch (TwitterException e) {
			e.printStackTrace();
		}
		tweet.setText("");
	}

	/**
	 * Statusからオブジェクトを作成
	 * @param status
	 * @return
	 */
	private Parent getObject(Status status){
		JavatterFxmlLoader<TweetObjectController> loader=new JavatterFxmlLoader<>();
		Parent p=null;
		try {
			p = loader.loadFxml("TweetObject.fxml");
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		TweetObjectController c=loader.getController();
		c.setAccountName("@"+status.getUser().getScreenName());
		c.setUserName(status.getUser().getName());
		c.setTweet(status.getText());
		c.setVia(status.getSource());
		try {
			c.setImage(status.getUser().getProfileImageURL());
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		return p;
	}

	private void createObject(JavatterLineController controller,Parent p) throws IOException{
		controller.addObject(p);
	}

	private boolean isReply(Status status) throws IllegalStateException, TwitterException{
		String user=TwitterManager.getInstance().getTwitter().getScreenName();
		return user.equals(status.getInReplyToScreenName());
	}
}
