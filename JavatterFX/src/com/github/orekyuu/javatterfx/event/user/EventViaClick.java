package com.github.orekyuu.javatterfx.event.user;

import java.net.URL;

import com.github.orekyuu.javatterfx.event.Event;

/**
 * Viaをクリックした時のイベント
 * @author kyuuban
 *
 */
public class EventViaClick implements Event{

	private URL url;
	private String name;
	/**
	 * 対象のURL
	 * @param url viaのURL
	 */
	public EventViaClick(URL url,String name){
		this.url=url;
	}

	/**
	 * クライアントのURLを返します
	 * @return
	 */
	public URL getURL(){
		return url;
	}

	/**
	 * クライアント名を返します
	 * @return
	 */
	public String getName(){
		return name;
	}
}
