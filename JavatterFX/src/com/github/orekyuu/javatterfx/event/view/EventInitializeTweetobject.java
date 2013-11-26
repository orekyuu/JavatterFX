package com.github.orekyuu.javatterfx.event.view;

import com.github.orekyuu.javatterfx.controller.TweetObjectController;
import com.github.orekyuu.javatterfx.event.Event;

/**
 * TweetObjectが作成された時のイベント
 * @author kyuuban
 *
 */
public class EventInitializeTweetobject implements Event{

	private TweetObjectController controller;
	public EventInitializeTweetobject(
			TweetObjectController controller) {
		this.controller=controller;
	}

	/**
	 * TweetObjectのコントローラを返します
	 * @return
	 */
	public TweetObjectController getController(){
		return controller;
	}

}
