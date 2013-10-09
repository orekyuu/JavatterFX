package com.github.orekyuu.javatterfx.view;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class JavatterFxmlLoader<T> {

	/**
	 * FXMLをロードします
	 * @param clazz クラス
	 * @param fxml 読み込みたいファイルの名前
	 * @return
	 * @throws IOException
	 */
	public final static Parent load(Class<?> clazz,String fxml) throws IOException{
		return FXMLLoader.load(clazz.getResource(fxml));
	}

	/**
	 * com,orekyuu.javatterfx.viewパッケージの中にあるファイルを読み込む
	 * @param fxml
	 * @return
	 * @throws IOException
	 */
	public final static Parent load(String fxml) throws IOException{
		return load(JavatterFxmlLoader.class, fxml);
	}

	private FXMLLoader loader=new FXMLLoader();

	public Parent loadFxml(Class<?> clazz,String fxml) throws IOException{
		Parent p=(Parent) loader.load(clazz.getResourceAsStream(fxml));
		return p;
	}

	public Parent loadFxml(String fxml) throws IOException{
		return loadFxml(JavatterFxmlLoader.class, fxml);
	}

	public T getController(){
		return loader.getController();
	}
}
