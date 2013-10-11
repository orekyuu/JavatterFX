package com.github.orekyuu.javatterfx.util;

import twitter4j.Paging;
import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;

import com.github.orekyuu.javatterfx.event.EventManager;
import com.github.orekyuu.javatterfx.event.stream.EventLoadHomeTimeline;
import com.github.orekyuu.javatterfx.event.stream.EventLoadMensions;

/**
 * Twitterでのアクションを補助するクラス
 * @author orekyuu
 *
 */
public final class TwitterUtil {

	private static int homeTimeLinePage=1;
	private static long homeTimeLineOldID=Long.MAX_VALUE;
	private static int mensionPage=1;
	private static long mensionOldID=Long.MAX_VALUE;

	/**
	 * 指定されたStatusをリツイートします
	 * @param twitter Twitterインスタンス
	 * @param status リツイートするつぶやき
	 * @throws TwitterException
	 */
	public static final void rt(final Twitter twitter,final Status status) throws TwitterException{
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
	public static final void fav(final Twitter twitter,final Status status){
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
	public static final void unfav(final Twitter twitter,final Status status) {
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

	/**
	 * タイムラインをロードさせる
	 * @param twitter
	 * @throws TwitterException
	 */
	public static final void loadHomeTimeLine(Twitter twitter) throws TwitterException{
		Paging page=new Paging(homeTimeLinePage,20);
		homeTimeLinePage++;
		ResponseList<Status> status = twitter.getHomeTimeline(page);
		for (int i=0;i<status.size();i++) {
			EventLoadHomeTimeline event=new EventLoadHomeTimeline(status.get(i));
			EventManager.INSTANCE.eventFire(event);
		}
	}

	/**
	 * メンションをロードさせる
	 * @param twitter
	 * @throws TwitterException
	 */
	public static final void loadMensions(Twitter twitter) throws TwitterException{
		Paging page=new Paging(mensionPage,20);
		mensionPage++;
		ResponseList<Status> status = twitter.getMentionsTimeline(page);
		for (int i=0;i<status.size();i++) {
			EventLoadMensions event=new EventLoadMensions(status.get(i));
			EventManager.INSTANCE.eventFire(event);
		}
	}

}
