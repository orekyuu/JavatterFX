package com.github.orekyuu.javatterfx.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javafx.embed.swing.SwingFXUtils;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;

import javax.imageio.ImageIO;

/**
 * アイコンのキャッシュ
 * @author orekyuu
 *
 */
public class IconCache {

	public static final File cacheDir=new File("cache");
	private static IconCache icon=new IconCache();
	private Map<URL,Image> cacheMap=new HashMap<URL,Image>();

	private IconCache(){

	}

	/**
	 * インスタンスを返す
	 * @return
	 */
	public static IconCache getInstance(){
		return icon;
	}

	/**
	 * アイコンを取得する
	 * @param url アイコンのURL
	 * @return
	 * @throws IOException
	 */
	public Image getIcon(URL url) throws IOException{
		if(cacheMap.containsKey(url)){
			return cacheMap.get(url);
		}
		Image icon=null;
		icon=getLocalIcon(url);
		/*if(icon!=null){
			cacheMap.put(url, icon);
			return icon;
		}*/

		HttpURLConnection http=(HttpURLConnection) url.openConnection();
		http.setRequestMethod("GET");
		http.connect();
		icon=new Image(http.getInputStream());
		http.disconnect();

		cacheMap.put(url, icon);
		if(JavatterConfig.getInstance().getUseLocalCache()){
			saveLocalIcon(url, icon);
		}
		return icon;
	}

	private Image getLocalIcon(URL url){
		String fileName=url.toString().replace('/', '_').replace(':', '_')+".png";
		File cache=new File(cacheDir, fileName);
		if(cache.exists()){
			try {
				Image icon=new Image(new FileInputStream(cache));
				return icon;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;

	}

	private void saveLocalIcon(URL url, Image icon){
		String fileName=url.toString().replace('/', '_').replace(':', '_')+".png";
		File cache=new File(cacheDir, fileName);
		cacheDir.mkdirs();
		Canvas canvas=new Canvas(icon.getWidth(),icon.getHeight());
		GraphicsContext gc = canvas.getGraphicsContext2D();
		gc.drawImage(icon, 0, 0);
		WritableImage wi=canvas.snapshot(null, new WritableImage((int)icon.getWidth(),(int)icon.getHeight()));
		try {
			ImageIO.write(SwingFXUtils.fromFXImage(wi, null), "png", cache);
		} catch (Exception s) {
			s.printStackTrace();
		}
	}
}
