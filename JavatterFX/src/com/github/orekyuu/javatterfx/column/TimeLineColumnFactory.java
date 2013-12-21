package com.github.orekyuu.javatterfx.column;

import java.io.IOException;

import javafx.scene.Parent;

import com.github.orekyuu.javatterfx.controller.JavatterTimeLineController;
import com.github.orekyuu.javatterfx.event.Listener;
import com.github.orekyuu.javatterfx.managers.ColumFactory;
import com.github.orekyuu.javatterfx.view.JavatterFxmlLoader;

public class TimeLineColumnFactory implements ColumFactory,Listener{

	@Override
	public String getColumName() {
		return "TimeLine";
	}

	@Override
	public Parent createView() {
		JavatterFxmlLoader<JavatterTimeLineController> loader=new JavatterFxmlLoader<>();
		Parent p=null;
		try {
			p=loader.loadFxml("Timeline.fxml");
		} catch (IOException e) {
			e.printStackTrace();
		}
		JavatterTimeLineController timelinecontroller=loader.getController();
		timelinecontroller.setName("TimeLine");
		return p;
	}

}
