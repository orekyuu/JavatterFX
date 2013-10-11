package com.github.orekyuu.javatterfx.util;

import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

/**
 * Twitterでのアクションを補助するクラス
 * @author orekyuu
 *
 */
public final class TwitterUtil {

	/**
	 * 指定されたStatusをリツイートします
	 * @param twitter Twitterインスタンス
	 * @param status リツイートするつぶやき
	 * @throws TwitterException
	 */
	public void rt(final Twitter twitter,final Status status) throws TwitterException{
		Thread th=new Thread(){
			@Override
			public void run(){
				try {
					if(canRetweet(status))
						twitter.retweetStatus(status.getId());
				} catch (TwitterException e) {
					e.printStackTrace();
				}
			}

			private boolean canRetweet(Status status) throws IllegalStateException, TwitterException{
				if(status.getUser().getScreenName().equals(twitter.getScreenName()))
					return false;
				if(status.isRetweet()){
					return canRetweet(status.getRetweetedStatus());
				}
				return true;
			}
		};
		th.start();
	}

	/**
	 * お気に入りに追加します
	 * @param twitter Twitterインスタンス
	 * @param status お気に入りに登録するつぶやき
	 */
	public void fav(final Twitter twitter,final Status status){
		Thread th=new Thread(){
			@Override
			public void run(){
				try {
					twitter.createFavorite(status.getId());
				} catch (TwitterException e) {
					e.printStackTrace();
				}
			}
		};
		th.start();
	}

	/**
	 * お気に入りから削除します
	 * @param twitter
	 * @param status
	 */
	public void unfav(final Twitter twitter,final Status status) {
		Thread th=new Thread(){
			@Override
			public void run(){
				try {
					twitter.destroyFavorite(status.getId());
				} catch (TwitterException e) {
					e.printStackTrace();
				}
			}
		};
		th.start();
	}

}
