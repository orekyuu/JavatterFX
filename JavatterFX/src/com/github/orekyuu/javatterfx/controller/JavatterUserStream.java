package com.github.orekyuu.javatterfx.controller;

import twitter4j.DirectMessage;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;
import twitter4j.UserList;
import twitter4j.UserStreamListener;

import com.github.orekyuu.javatterfx.account.AccountManager;
import com.github.orekyuu.javatterfx.account.TwitterManager;
import com.github.orekyuu.javatterfx.event.EventManager;
import com.github.orekyuu.javatterfx.event.stream.EventBlock;
import com.github.orekyuu.javatterfx.event.stream.EventDirectMessage;
import com.github.orekyuu.javatterfx.event.stream.EventFavorite;
import com.github.orekyuu.javatterfx.event.stream.EventFollow;
import com.github.orekyuu.javatterfx.event.stream.EventOnFriendList;
import com.github.orekyuu.javatterfx.event.stream.EventScrubGeo;
import com.github.orekyuu.javatterfx.event.stream.EventStallWarning;
import com.github.orekyuu.javatterfx.event.stream.EventStatus;
import com.github.orekyuu.javatterfx.event.stream.EventTrackLimitationNotice;
import com.github.orekyuu.javatterfx.event.stream.EventTweetRemove;
import com.github.orekyuu.javatterfx.event.stream.EventUnblock;
import com.github.orekyuu.javatterfx.event.stream.EventUnfavorite;
import com.github.orekyuu.javatterfx.event.stream.EventUserListCreation;
import com.github.orekyuu.javatterfx.event.stream.EventUserListDeletion;
import com.github.orekyuu.javatterfx.event.stream.EventUserListMemberAddition;
import com.github.orekyuu.javatterfx.event.stream.EventUserListMemberDeletion;
import com.github.orekyuu.javatterfx.event.stream.EventUserListSubscription;
import com.github.orekyuu.javatterfx.event.stream.EventUserListUnscription;
import com.github.orekyuu.javatterfx.event.stream.EventUserListUpdate;
import com.github.orekyuu.javatterfx.event.stream.EventUserProfileUpdate;
import com.github.orekyuu.javatterfx.event.stream.EventUserStreamException;

public class JavatterUserStream implements UserStreamListener{

	/**
	 * ユーザーストリームを開始する
	 */
	public void start(){
		TwitterManager m=TwitterManager.getInstance();
		TwitterStream twitterStream = new TwitterStreamFactory().getInstance();
		twitterStream.setOAuthConsumer(m.getConsumerKey(), m.getConsumerSecret());
		twitterStream.setOAuthAccessToken(AccountManager.getInstance().getAccessToken());
		twitterStream.addListener(this);
		twitterStream.user();
	}

	@Override
	public void onDeletionNotice(StatusDeletionNotice arg0) {
		EventTweetRemove event=new EventTweetRemove(arg0);
		EventManager.INSTANCE.eventFire(event);
	}

	@Override
	public void onScrubGeo(long arg0, long arg1) {
		EventScrubGeo event=new EventScrubGeo(arg0, arg1);
		EventManager.INSTANCE.eventFire(event);
	}

	@Override
	public void onStallWarning(StallWarning arg0) {
		EventStallWarning event=new EventStallWarning(arg0);
		EventManager.INSTANCE.eventFire(event);
	}

	@Override
	public void onStatus(Status arg0) {
		EventStatus event=new EventStatus(arg0);
		EventManager.INSTANCE.eventFire(event);
	}

	@Override
	public void onTrackLimitationNotice(int arg0) {
		EventTrackLimitationNotice event=new EventTrackLimitationNotice(arg0);
		EventManager.INSTANCE.eventFire(event);
	}

	@Override
	public void onException(Exception arg0) {
		EventUserStreamException event=new EventUserStreamException(arg0);
		EventManager.INSTANCE.eventFire(event);
	}

	@Override
	public void onBlock(User arg0, User arg1) {
		EventBlock event=new EventBlock(arg0,arg1);
		EventManager.INSTANCE.eventFire(event);
	}

	@Override
	public void onDeletionNotice(long arg0, long arg1) {
		//たぶん onDeletionNotice(StatusDeletionNotice arg0)と一緒なのでスルー
	}

	@Override
	public void onDirectMessage(DirectMessage arg0) {
		EventDirectMessage event = new EventDirectMessage(arg0);
		EventManager.INSTANCE.eventFire(event);
	}

	@Override
	public void onFavorite(User arg0, User arg1, Status arg2) {
		EventFavorite event=new EventFavorite(arg0,arg1,arg2);
		EventManager.INSTANCE.eventFire(event);
	}

	@Override
	public void onFollow(User arg0, User arg1) {
		EventFollow event=new EventFollow(arg0,arg1);
		EventManager.INSTANCE.eventFire(event);
	}

	@Override
	public void onFriendList(long[] arg0) {
		EventOnFriendList event=new EventOnFriendList(arg0);
		EventManager.INSTANCE.eventFire(event);
	}

	@Override
	public void onUnblock(User arg0, User arg1) {
		EventUnblock event=new EventUnblock(arg0, arg1);
		EventManager.INSTANCE.eventFire(event);
	}

	@Override
	public void onUnfavorite(User arg0, User arg1, Status arg2) {
		EventUnfavorite event=new EventUnfavorite(arg0, arg1, arg2);
		EventManager.INSTANCE.eventFire(event);
	}

	@Override
	public void onUserListCreation(User arg0, UserList arg1) {
		EventUserListCreation event=new EventUserListCreation(arg0, arg1);
		EventManager.INSTANCE.eventFire(event);
	}

	@Override
	public void onUserListDeletion(User arg0, UserList arg1) {
		EventUserListDeletion event=new EventUserListDeletion(arg0, arg1);
		EventManager.INSTANCE.eventFire(event);
	}

	@Override
	public void onUserListMemberAddition(User arg0, User arg1, UserList arg2) {
		EventUserListMemberAddition event=new EventUserListMemberAddition(arg0, arg1, arg2);
		EventManager.INSTANCE.eventFire(event);
	}

	@Override
	public void onUserListMemberDeletion(User arg0, User arg1, UserList arg2) {
		EventUserListMemberDeletion event=new EventUserListMemberDeletion(arg0, arg1, arg2);
		EventManager.INSTANCE.eventFire(event);
	}

	@Override
	public void onUserListSubscription(User arg0, User arg1, UserList arg2) {
		EventUserListSubscription event=new EventUserListSubscription(arg0, arg1, arg2);
		EventManager.INSTANCE.eventFire(event);
	}

	@Override
	public void onUserListUnsubscription(User arg0, User arg1, UserList arg2) {
		EventUserListUnscription event=new EventUserListUnscription(arg0, arg1, arg2);
		EventManager.INSTANCE.eventFire(event);
	}

	@Override
	public void onUserListUpdate(User arg0, UserList arg1) {
		EventUserListUpdate event=new EventUserListUpdate(arg0, arg1);
		EventManager.INSTANCE.eventFire(event);
	}

	@Override
	public void onUserProfileUpdate(User arg0) {
		EventUserProfileUpdate event=new EventUserProfileUpdate(arg0);
		EventManager.INSTANCE.eventFire(event);
	}
}
