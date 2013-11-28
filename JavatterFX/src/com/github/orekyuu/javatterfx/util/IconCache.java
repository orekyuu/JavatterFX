package com.github.orekyuu.javatterfx.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javafx.scene.image.Image;

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
		// TODO ダサい
		Image image = null;
		if((image = getIconFromCacheMap(url)) != null) {
			return image;
		}else if((image = getIconFromLocalCache(url)) != null) {
			cacheMap.put(url, image);
			return image;
		}else if((image = getIconFromHttp(url)) != null) {
			cacheMap.put(url, image);
			return image;
		}
		throw new IOException("不明な読み込みエラー");
	}

	/**
	 * cacheMapからアイコンを取得する
	 * @param url
	 * @return キャッシュが存在する場合はImage、それ以外はnull
	 */
	private Image getIconFromCacheMap(URL url) {
		return cacheMap.get(url);
	}

	/**
	 * LocalCacheからアイコンを取得する
	 * @param url
	 * @return キャッシュが存在する場合はImage、それ以外はnull
	 */
	private Image getIconFromLocalCache(URL url) {
		return getIconFromLocalCache(url, false);
	}

	/**
	 * LocalCacheからアイコンを取得する
	 * @param url
	 * @param force 強制的にLocalCacheから取得する
	 * @return キャッシュが存在する場合はImage、それ以外はnull
	 */
	private Image getIconFromLocalCache(URL url, boolean force) {
		if(!JavatterConfig.getInstance().getUseLocalCache() && !force) {
			return null;
		}

		File cacheFile = getCacheFile(url);
		if(!cacheFile.exists() ) {
			return null;
		}else{
			try {
				return new Image(new FileInputStream(cacheFile));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return null;
			}
		}
	}

	private Image getIconFromHttp(URL url) {
		try {
			if(JavatterConfig.getInstance().getUseLocalCache() && saveCacheFile(url)){
				return getIconFromLocalCache(url);
			}

			Image image = new Image(url.openStream());
			return image;
		} catch (IOException e) {
			// TODO 接続失敗
			e.printStackTrace();
			return null;
		}
	}

	private boolean saveCacheFile(URL url) throws IOException {
		createCacheFolder();
		InputStream stream = url.openStream();
		try {
			Files.copy(stream, Paths.get(getCacheFile(url).getPath()));
			return true;
		}catch(IOException ex) {
			return false;
		}
	}

	private void createCacheFolder(){
		if(!cacheDir.exists())
			cacheDir.mkdir();
	}

	private File getCacheFile(URL url) {
		String fileName=url.toString().replace('/', '_').replace(':', '_');
		File cache=new File(cacheDir, fileName);
		return cache;
	}
}
