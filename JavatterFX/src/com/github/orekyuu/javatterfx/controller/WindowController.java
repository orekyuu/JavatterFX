package com.github.orekyuu.javatterfx.controller;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.ToolBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import twitter4j.Status;
import twitter4j.TwitterException;

import com.github.orekyuu.javatterfx.account.TwitterManager;
import com.github.orekyuu.javatterfx.column.ColumnManager;
import com.github.orekyuu.javatterfx.event.EventHandler;
import com.github.orekyuu.javatterfx.event.EventManager;
import com.github.orekyuu.javatterfx.event.Listener;
import com.github.orekyuu.javatterfx.event.stream.EventLoadHomeTimeline;
import com.github.orekyuu.javatterfx.event.stream.EventLoadMensions;
import com.github.orekyuu.javatterfx.event.stream.EventStatus;
import com.github.orekyuu.javatterfx.event.system.EventPluginLoad;
import com.github.orekyuu.javatterfx.event.user.EventReplyClick;
import com.github.orekyuu.javatterfx.event.user.EventUserTweet;
import com.github.orekyuu.javatterfx.event.user.EventUserTweet.EventType;
import com.github.orekyuu.javatterfx.main.Main;
import com.github.orekyuu.javatterfx.util.JavatterConfig;
import com.github.orekyuu.javatterfx.util.StatusUpdateBuilder;
import com.github.orekyuu.javatterfx.util.TweetDispenser;
import com.github.orekyuu.javatterfx.view.JavatterFxmlLoader;


public class WindowController implements Initializable, Listener{

	@FXML
	private BorderPane root;
    @FXML
    private VBox topborder;
    @FXML
    private TextArea tweet;
    @FXML
    private ToolBar bar;
    @FXML
    private MenuButton config;
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
    @FXML
    private CheckMenuItem beamRT;
    @FXML
    private CheckMenuItem useCache;
    @FXML
    private MenuButton column;
    @FXML
    private Button koukoku;

    private Status reply;
    /**
     * 初期化処理
     */
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		useCache.setSelected(JavatterConfig.getInstance().getUseLocalCache());
		beamRT.setSelected(JavatterConfig.getInstance().getJavaBeamRT());
		addChilden(ColumnManager.INSTANCE.getColumFactory("TimeLine").createView());
		addChilden(ColumnManager.INSTANCE.getColumFactory("Mensions").createView());
		EventManager.INSTANCE.addEventListener(this);
		for(String s:ColumnManager.INSTANCE.columList()){
			final MenuItem item=new MenuItem(s);
			item.setOnAction(new javafx.event.EventHandler<ActionEvent>() {

				@Override
				public void handle(ActionEvent event) {
					addChilden(ColumnManager.INSTANCE.getColumFactory(item.getText()).createView());
				}
			});
			column.getItems().add(item);
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
	public void onPluginLoad(final EventPluginLoad event){
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				MenuItem item=new MenuItem(event.getName());
				plugin.getItems().add(item);
			}
		});
	}

	@EventHandler
	public void onReply(EventReplyClick event){
		final Status status=event.getStatus();
		reply=status;
		Platform.runLater(new Runnable() {

			@Override
			public void run() {
				try {
					StringBuilder builder=new StringBuilder();
					builder.append(tweet.getText()).append("@").append(status.getUser().getScreenName())
					.append(" ");
					tweet.setText(builder.toString());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

    public void onJavaBeam(ActionEvent event) {
		javaBeam();
	}

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
		TweetDispenser.tweet("JavaFXビームﾋﾞﾋﾞﾋﾞﾋﾞﾋﾞwwwww");
	}

	private void tweet(EventType type){
		EventUserTweet event=new EventUserTweet(tweet.getText(),TwitterManager.getInstance().getUser(),type);
		EventManager.INSTANCE.eventFire(event);
		StatusUpdateBuilder builder=new StatusUpdateBuilder(event.getText());
		builder.setReplyID(reply);
		TweetDispenser.tweet(builder.create());
		tweet.setText("");
		reply=null;
	}

	public void onBeamConfig(ActionEvent event){
		JavatterConfig.getInstance().setJavaBeamRT(beamRT.isSelected());
	}

	public void onCacheConfig(ActionEvent event){
		JavatterConfig.getInstance().setUseLocalCache(useCache.isSelected());
	}

	public void coukoku(ActionEvent event){
		TweetDispenser.tweet("嘘、私のJavaビーム...弱すぎ？ そんなあなたにJava力トレーニングソフトJavatterFX! 無料でJava力を鍛えて周りのみんなを圧倒しよう！ ダウンロードはこちら→http://www1221uj.sakura.ne.jp/wordpress/ #javatter");
	}
}
