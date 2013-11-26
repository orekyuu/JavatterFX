package com.github.orekyuu.javatterfx.listeners;

import java.util.regex.Pattern;

import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import twitter4j.Status;
import twitter4j.TwitterException;

import com.github.orekyuu.javatterfx.account.TwitterManager;
import com.github.orekyuu.javatterfx.event.EventHandler;
import com.github.orekyuu.javatterfx.event.Listener;
import com.github.orekyuu.javatterfx.event.stream.EventStatus;
import com.github.orekyuu.javatterfx.event.view.EventInitializeTweetobject;
import com.github.orekyuu.javatterfx.util.JavatterConfig;
import com.github.orekyuu.javatterfx.util.StatusUpdateBuilder;
import com.github.orekyuu.javatterfx.util.TweetDispenser;
import com.github.orekyuu.javatterfx.util.TwitterUtil;

public class JavaBeamRT implements Listener {

	@EventHandler
	public void onStatus(EventStatus event){
		if(isJavaBeam(event.getTweetText())&&JavatterConfig.getInstance().getJavaBeamRT()){
			try {
				TwitterUtil.rt(TwitterManager.getInstance().getTwitter(), event.getStatus());
			} catch (TwitterException e) {
				e.printStackTrace();
			}
		}
	}

	@EventHandler
	public void onTweetCreate(final EventInitializeTweetobject event){
		MenuItem item=new MenuItem("Javaビーム");
		event.getController().addMenuItem(item);

		item.setOnAction(new javafx.event.EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				Status status=event.getController().getStatus();
				String user=status.getUser().getScreenName();
				StatusUpdateBuilder builder=new StatusUpdateBuilder("@"+user+" Javaビームﾋﾞﾋﾞﾋﾞﾋﾞﾋﾞﾋﾞwwwwwwwwww");
				builder.setReplyID(status);
				TweetDispenser.tweet(builder.create());
			}
		});
	}

	private boolean isJavaBeam(String text){
		String regex="^[JjＪｊ][AaＡａ][VvＶｖ][AaＡａ]([FfＦｆ][XxＸｘ])*([ビび]|ﾋﾞ)[ーｰ]{1,3}[ムﾑむ]([ビび]|ﾋﾞ){3,}[WwＷｗ]{3,}$";
		Pattern p=Pattern.compile(regex);
		return p.matcher(text).find();
	}
}
