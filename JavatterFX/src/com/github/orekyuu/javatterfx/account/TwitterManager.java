package com.github.orekyuu.javatterfx.account;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;

/**
 * Twitter管理クラス
 * @author orekyuu
 *
 */
public class TwitterManager {

	private static TwitterManager manager=new TwitterManager();
	private static Twitter twitter;

	private String consumerKey="LVWccGK2KDACq3sEblpw";
	private String consumerSecret="fj4UDvwc6d6VfRg7xk25c7BHOcz9IHLZQAuAiJqDs";
	private User user;

	private TwitterManager(){
		twitter=TwitterFactory.getSingleton();
		twitter.setOAuthConsumer(consumerKey, consumerSecret);
	}

	/**
	 * インスタンスを取得します
	 * @return
	 */
	public static TwitterManager getInstance(){
		return manager;
	}

	/**
	 * Twitterインスタンスを取得します
	 * @return
	 */
	public Twitter getTwitter(){
		return twitter;
	}

	/**
	 * コンシューマキーを返します
	 * @return
	 */
	public String getConsumerKey(){
		return consumerKey;
	}

	/**
	 * コンシューマシークレットを返します
	 * @return
	 */
	public String getConsumerSecret(){
		return consumerSecret;
	}

	public User getUser(){
		return user;
	}

	/**
	 * アクセストークンを使って認証します
	 * @param token アクセストークン
	 */
	public void authentication(AccessToken token){
		getTwitter().setOAuthAccessToken(token);
		try {
			user=twitter.showUser(token.getUserId());
		} catch (TwitterException e) {
			e.printStackTrace();
		}
	}
}
