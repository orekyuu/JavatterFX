package com.github.orekyuu.javatterfx.controller;

import java.net.URL;
import java.util.ResourceBundle;

import com.sun.javafx.tk.Toolkit;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class JavatterLineController implements Initializable{

	@FXML
	private BorderPane root;

	@FXML
	private ToolBar bar;

	@FXML
	private Label name;

	@FXML
	private VBox box;

	@FXML
	private ScrollPane scroll;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

	}

	public void setName(String s){
		name.setText(s);
	}

	public void addObject(Parent p) {
		box.getChildren().add(0, p);
	}

}
