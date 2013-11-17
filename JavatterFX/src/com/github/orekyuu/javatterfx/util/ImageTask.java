package com.github.orekyuu.javatterfx.util;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/**
 * 別スレッドで画像を読み込んで、ImageViewに結果を表示します
 * @author kyuuban
 *
 */
public class ImageTask implements Runnable{

	private URL url;
	private ImageView view;
	/**
	 * 指定された画像を読み込みます
	 * @param view 結果を表示するImageView
	 * @param img 画像のURL
	 */
	public ImageTask(ImageView view,String img){
		this.view=view;
		try {
			this.url=new URL(img);
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 指定された画像を読み込みます
	 * @param view 結果を表示するImageView
	 * @param url 画像のURL
	 */
	public ImageTask(ImageView view,URL url){
		this.view=view;
		this.url=url;
	}

	@Override
	public void run(){
		try {
			final Image icon=IconCache.getInstance().getIcon(url);
			Platform.runLater(new Runnable() {

				@Override
				public void run() {
					view.setImage(icon);
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 画像の読み込みを開始
	 */
	public void start(){
		Thread th=new Thread(this);
		th.start();
	}
}
