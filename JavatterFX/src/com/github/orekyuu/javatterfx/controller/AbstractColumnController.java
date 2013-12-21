package com.github.orekyuu.javatterfx.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;

import com.github.orekyuu.javatterfx.event.Listener;
import com.github.orekyuu.javatterfx.managers.EventManager;

public abstract class AbstractColumnController implements Initializable,Listener{

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		EventManager.INSTANCE.addEventListener(this);
	}

	/**
	 * Columnを閉じます
	 * @param root
	 */
	public void close(Parent root){
		HBox box=(HBox) root.getParent();
		box.getChildren().remove(root);
		EventManager.INSTANCE.removeEventListener(this);
	}
}
