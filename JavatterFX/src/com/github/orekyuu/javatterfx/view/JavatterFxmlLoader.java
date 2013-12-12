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
	 * com.orekyuu.javatterfx.viewパッケージの中にあるファイルを読み込む
	 * @param fxml
	 * @return
	 * @throws IOException
	 */
	public final static Parent load(String fxml) throws IOException{
		return load(JavatterFxmlLoader.class, fxml);
	}

	private FXMLLoader loader=new FXMLLoader();

	/**
	 * 第一引数のClassをのgetResourceAsStreamから得たInputStreamを利用してFXMLを読み込みます
	 * @param clazz
	 * @param fxml 読み込みたいFXMLのファイル名
	 * @return
	 * @throws IOException
	 */
	public Parent loadFxml(Class<?> clazz,String fxml) throws IOException{
		Parent p=(Parent) loader.load(clazz.getResourceAsStream(fxml));
		return p;
	}

	/**
	 * com.orekyuu.javatterfx.viewパッケージの中にある引数のFXMLを読み込みます
	 * @param fxml
	 * @return
	 * @throws IOException
	 */
	public Parent loadFxml(String fxml) throws IOException{
		return loadFxml(JavatterFxmlLoader.class, fxml);
	}

	/**
	 * FXMLに定義したControllerクラスのインスタンスを返します<br>
	 * loadFxmlメソッドを呼び出してロードした後で使用してください。<br>
	 * @return FXMLに定義したControllerインスタンス
	 */
	public T getController(){
		return loader.getController();
	}
}
