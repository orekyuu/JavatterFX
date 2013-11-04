package com.github.orekyuu.javatterfx.listeners;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URISyntaxException;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import com.github.orekyuu.javatterfx.controller.UserWindowController;
import com.github.orekyuu.javatterfx.event.EventHandler;
import com.github.orekyuu.javatterfx.event.Listener;
import com.github.orekyuu.javatterfx.event.user.EventIconClick;
import com.github.orekyuu.javatterfx.event.user.EventViaClick;
import com.github.orekyuu.javatterfx.view.JavatterFxmlLoader;

public class TweetObjectListener implements Listener{

	@EventHandler
	public void openUserWindow(EventIconClick click){
		try {
			JavatterFxmlLoader<UserWindowController> loader=new JavatterFxmlLoader<>();
			Parent p = loader.loadFxml("UserWindow.fxml");
			loader.getController().setUser(click.getUser());
			Scene scene=new Scene(p);
			Stage stage=new Stage();
			stage.setScene(scene);
			stage.setTitle(click.getUser().getName());
			stage.centerOnScreen();
			stage.setWidth(340);
			stage.setMaxWidth(340);
			stage.setMinWidth(340);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@EventHandler
	public void openBrowser(EventViaClick via){
		try {
			Desktop.getDesktop().browse(via.getURL().toURI());
		} catch (IOException | URISyntaxException e) {
			e.printStackTrace();
		}
	}
}