package com.github.orekyuu.javatterfx.column;

import java.io.IOException;

import javafx.scene.Parent;

import com.github.orekyuu.javatterfx.controller.MensionsController;
import com.github.orekyuu.javatterfx.view.JavatterFxmlLoader;

public class MensionsColumnFactory implements ColumFactory{

	@Override
	public String getColumName() {
		return "Mensions";
	}

	@Override
	public Parent createView() {
		JavatterFxmlLoader<MensionsController> loader=new JavatterFxmlLoader<>();
		Parent p=null;
		try {
			p=loader.loadFxml("Mensions.fxml");
		} catch (IOException e) {
			e.printStackTrace();
		}
		MensionsController controller=loader.getController();
		controller.setName("Mensions");
		return p;
	}

}
