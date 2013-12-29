package orekyuu.javabeam;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import orekyuu.plugin.loader.Plugin;

import com.github.orekyuu.javatterfx.managers.EventManager;
import com.github.orekyuu.javatterfx.managers.SaveData;

/**
 * Javaビーム機能を提供するプラグイン
 * @author orekyuuPC
 *
 */
@Plugin(name = "JavaBeamUtil", version = "1.0")
public class PluginJavaBeam {

	/**
	 * Pluginメニューに表示されるMenu
	 */
	@Plugin.ConfigItem
	private Menu item;

	/**
	 * 情報を保存するSaveData
	 */
	@Plugin.SaveData
	private SaveData data;

	/**
	 * 初期化処理
	 */
	@Plugin.Init
	public void init(){
		EventManager.INSTANCE.addEventListener(new JavaBeamListener());//イベントリスナを登録
		data.setDefaultValue("JavaBeamRT", false);//初期値を設定
		data.setDefaultValue("JavaBeamFav", false);//初期値を設定
		JavaBeamConfig.INSTANCE.init();//プラグインのコンフィグを初期化

		final CheckMenuItem rt=new CheckMenuItem("JavaBeamRT");//JavaBeamオートRTのコンフィグメニューを作成
		rt.setSelected(JavaBeamConfig.INSTANCE.getJavaBeamRT());//SaveDataによって値を設定
		rt.setOnAction(new EventHandler<ActionEvent>() {//クリックされた時のイベント

			@Override
			public void handle(ActionEvent paramT) {
				JavaBeamConfig.INSTANCE.setJavaBeamRT(rt.isSelected());//選択状態をSaveDataに保存
			}
		});
		final CheckMenuItem fav=new CheckMenuItem("JavaBeamFav");//JavaBeamオートFavのコンフィグメニューを作成
		fav.setSelected(JavaBeamConfig.INSTANCE.getJavaBeamFav());//SaveDataによって値を設定
		fav.setOnAction(new EventHandler<ActionEvent>() {//クリックされた時のイベント

			@Override
			public void handle(ActionEvent paramT) {
				JavaBeamConfig.INSTANCE.setJavaBeamFav(fav.isSelected());//選択状態をSaveDataに保存
			}
		});

		item.getItems().addAll(rt,fav);//チェックメニューをMenuに登録
	}
}
