package com.github.orekyuu.javatterfx.util;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.github.orekyuu.javatterfx.account.TwitterManager;

import twitter4j.StatusUpdate;
import twitter4j.TwitterException;

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

	public TweetDispenser(){
	}

	public synchronized static void tweet(String s){
		tweet(new StatusUpdate(s));
	}

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
}
