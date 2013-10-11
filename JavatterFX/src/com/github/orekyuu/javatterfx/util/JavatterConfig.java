package com.github.orekyuu.javatterfx.util;

import java.io.File;

/**
 * Javatterのコンフィグクラス
 * @author orekyuu
 *
 */
public class JavatterConfig {

	private static JavatterConfig instance=new JavatterConfig();

	private SaveData savedata;

	private JavatterConfig(){

	}

	/**
	 * コンフィグを初期化
	 */
	public void init(){
		savedata=new SaveData("JavatterConfig", new File("SaveData/JavatterConfig"));
		SaveDataManager.getInstance().registerSaveData(savedata);
		savedata.setDefaultValue("JavaBeamRT", false);
		savedata.setDefaultValue("useLocalCache", true);
	}

	/**
	 * インスタンスを返す
	 * @return
	 */
	public static JavatterConfig getInstance(){
		return instance;
	}

	/**
	 * JavaビームRTのフラグを設定する
	 * @param flag
	 */
	public void setJavaBeamRT(boolean flag){
		savedata.setBoolean("JavaBeamRT", flag);
	}

	/**
	 * JavaビームRTの設定を返す
	 * @return
	 */
	public boolean getJavaBeamRT(){
		return savedata.getBoolean("JavaBeamRT");
	}

	/**
	 * ローカルキャッシュのフラグを設定する
	 * @param flag
	 */
	public void setUseLocalCache(boolean flag){
		savedata.setBoolean("useLocalCache", flag);
	}

	/**
	 * ローカルキャッシュの設定を返す
	 * @return
	 */
	public boolean getUseLocalCache(){
		return savedata.getBoolean("useLocalCache");
	}
}
