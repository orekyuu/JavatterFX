package com.github.orekyuu.javatterfx.event.view;

import javafx.scene.control.Button;
import javafx.scene.control.MenuButton;
import javafx.scene.control.ToolBar;

import com.github.orekyuu.javatterfx.event.Event;

/**
 * ツールバーが作成された時のイベント
 * @author kyuuban
 *
 */
public class EventToolbarCreated implements Event{

	private ToolBar toolbar;
	private MenuButton config;
	private Button koukoku;
	private Button tweet;

	public EventToolbarCreated(ToolBar bar,MenuButton config,Button koukoku,Button tweet){
		this.toolbar=bar;
		this.config=config;
		this.koukoku=koukoku;
		this.tweet=tweet;
	}

	public ToolBar getToolbar(){
		return toolbar;
	}

	public MenuButton getConfig(){
		return config;
	}

	public Button getKoukoku() {
		return koukoku;
	}

	public Button getTweet() {
		return tweet;
	}
}
