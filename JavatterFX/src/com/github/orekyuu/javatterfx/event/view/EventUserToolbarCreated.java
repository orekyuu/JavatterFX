package com.github.orekyuu.javatterfx.event.view;

import java.util.Map;

import javafx.scene.Node;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.control.ToolBar;
import twitter4j.User;

import com.github.orekyuu.javatterfx.event.Event;

/**
 * ユーザーウィンドウのツールバーが作成された時のイベント<br>
 * 新しいパネルの追加を行う場合はこれで行う
 * @author kyuuban
 *
 */
public class EventUserToolbarCreated implements Event{

	private User user;
	private ToolBar bar;
	private ToggleGroup group;
	private Map<ToggleButton,Node> map;
	public EventUserToolbarCreated(User user,ToolBar bar,ToggleGroup group,Map<ToggleButton,Node> map){
		this.user=user;
		this.bar=bar;
		this.group=group;
		this.map=map;
	}

	/**
	 * ユーザーの情報を得ます
	 * @return
	 */
	public User getUser(){
		return user;
	}

	/**
	 * ページを追加します
	 * @param button ボタン
	 * @param page 表示するページ
	 */
	public void addPage(ToggleButton button,Node page){
		button.setToggleGroup(group);
		bar.getItems().add(button);
		map.put(button, page);
	}
}
