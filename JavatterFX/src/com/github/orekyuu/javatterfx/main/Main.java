package com.github.orekyuu.javatterfx.main;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import twitter4j.TwitterException;
import twitter4j.auth.AccessToken;

import com.github.orekyuu.javatterfx.account.AccountManager;
import com.github.orekyuu.javatterfx.account.TwitterManager;
import com.github.orekyuu.javatterfx.controller.JavatterUserStream;
import com.github.orekyuu.javatterfx.util.JavatterConfig;
import com.github.orekyuu.javatterfx.util.SaveDataManager;
import com.github.orekyuu.javatterfx.util.TwitterUtil;
import com.github.orekyuu.javatterfx.view.JavatterCss;
import com.github.orekyuu.javatterfx.view.JavatterFxmlLoader;


public class Main extends Application{

	private static Stage stage;

	private static JavatterUserStream userstream;

	public static final boolean DEBUG=false;

	@Override
	public void start(Stage stage) throws Exception {
		Main.stage=stage;
		SaveDataManager.getInstance().init();
		JavatterConfig.getInstance().init();
		userstream=new JavatterUserStream();

		{
			Parent parent=JavatterFxmlLoader.load("Window.fxml");
			Scene scene=new Scene(parent);
			scene.getStylesheets().addAll(JavatterCss.getCssPath());
			stage.setTitle("JavatterFX");
			stage.setScene(scene);
			stage.show();
		}

		if(AccountManager.getInstance().isLogined()){
			AccessToken token = AccountManager.getInstance().getAccessToken();
			TwitterManager.getInstance().getTwitter().setOAuthAccessToken(token);
			TwitterManager.getInstance().authentication(token);
		}else{
			Parent parent=JavatterFxmlLoader.load("Login.fxml");
			Scene scene=new Scene(parent);
			scene.getStylesheets().addAll(JavatterCss.getCssPath());
			Stage stage1=new Stage();
			stage1.setTitle("Login");
			stage1.setScene(scene);
			stage1.centerOnScreen();
			stage1.showAndWait();
		}

		if(!DEBUG)
		{
			startingLoadAndStartUserStream();
		}
	}

	private void startingLoadAndStartUserStream(){
		BlockingQueue<Runnable> queue=new LinkedBlockingQueue<>();
		ThreadPoolExecutor executor=new ThreadPoolExecutor(3, 3, 1, TimeUnit.MINUTES, queue,new ThreadFactory() {

			@Override
			public Thread newThread(Runnable r) {
				Thread th=new Thread(r);
				th.setDaemon(true);
				return th;
			}
		});
		executor.execute(new Runnable() {

			@Override
			public void run() {
				try {
					TwitterUtil.loadHomeTimeLine(TwitterManager.getInstance().getTwitter());
					TwitterUtil.loadMensions(TwitterManager.getInstance().getTwitter());
				} catch (TwitterException e) {
					e.printStackTrace();
				}
			}
		});
		executor.execute(new Runnable() {

			@Override
			public void run() {
				Main.getUserStream().start();
			}
		});
		executor.shutdown();
	}

	public static void main(String[] args){
		launch(args);
	}

	public static Stage getStage(){
		return stage;
	}

	public static JavatterUserStream getUserStream(){
		return userstream;
	}

}
