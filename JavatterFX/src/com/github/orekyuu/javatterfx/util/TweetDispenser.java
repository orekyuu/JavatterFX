package com.github.orekyuu.javatterfx.util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.github.orekyuu.javatterfx.account.TwitterManager;

import twitter4j.StatusUpdate;
import twitter4j.TwitterException;

/**
 * ツイートを行うためのクラスです<br>
 * TwitterインスタンスはTwitterManagerのものを使用します
 * @author kyuuban
 *
 */
public class TweetDispenser {

	private static BlockingQueue<Runnable> queue=new LinkedBlockingQueue<>();
	private static ThreadPoolExecutor executor=new ThreadPoolExecutor(3,100,1,TimeUnit.MINUTES,queue, new ThreadFactory() {

		@Override
		public Thread newThread(Runnable r) {
			Thread th=new Thread(r);
			th.setDaemon(true);
			return th;
		}
	});

	/**
	 * 指定された文字列でツイートをします<br>
	 * ツイートは一度Queueに保存されて順に処理を行うので順番が保証されます<br>
	 * @param text 呟くテキスト
	 */
	public synchronized static void tweet(String text){
		tweet(new StatusUpdate(text));
	}

	/**
	 * 指定されたUpdateStatusでツイートをします<br>
	 * ツイートは一度Queueに保存されて順に処理を行うので順番が保証されます<br>
	 * @param status 呟くStatusUpdate
	 */
	public synchronized static void tweet(final StatusUpdate status){
		executor.execute(new Runnable() {
			@Override
			public void run() {
				try {
					TwitterManager.getInstance().getTwitter().updateStatus(status);
				} catch (TwitterException e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * 指定されたStatusUpdateを非同期でツイートします<br>
	 * Queueに保存されることなく別スレッドで行うので順番は保存されません<br>
	 * @param update 呟くStatusUpdate
	 */
	public static void asynchronousTweet(final StatusUpdate update){
		Thread th=new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					TwitterManager.getInstance().getTwitter().updateStatus(update);
				} catch (TwitterException e) {
					e.printStackTrace();
				}
			}
		});
		th.start();
	}

	/**
	 * 指定された文字列を非同期でツイートします<br>
	 * Queueに保存されることなく別スレッドで行うので順番は保存されません<br>
	 * @param tweet 呟く文字列
	 */
	public static void asynchronousTweet(String tweet){
		asynchronousTweet(new StatusUpdate(tweet));
	}
}
