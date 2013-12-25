package orekyuu.javabeam;

import java.io.File;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.CheckMenuItem;
import javafx.scene.control.Menu;
import orekyuu.plugin.loader.Plugin;

import com.github.orekyuu.javatterfx.managers.EventManager;
import com.github.orekyuu.javatterfx.managers.SaveData;
import com.github.orekyuu.javatterfx.managers.SaveDataManager;

@Plugin(name = "JavaBeamUtil", version = "1.0")
public class PluginJavaBeam {

	@Plugin.ConfigItem
	private Menu item;

	@Plugin.Init
	public void init(){
		EventManager.INSTANCE.addEventListener(new JavaBeamListener());
		final SaveData data=new SaveData("JavaBeamUtil", new File("SaveData/JavaBeamUtil"));
		SaveDataManager.getInstance().registerSaveData(data);
		data.setDefaultValue("JavaBeamRT", false);
		data.setDefaultValue("JavaBeamFav", false);
		JavaBeamConfig.INSTANCE.init();

		final CheckMenuItem rt=new CheckMenuItem("JavaBeamRT");
		rt.setSelected(JavaBeamConfig.INSTANCE.getJavaBeamRT());
		rt.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent paramT) {
				JavaBeamConfig.INSTANCE.setJavaBeamRT(rt.isSelected());
			}
		});
		final CheckMenuItem fav=new CheckMenuItem("JavaBeamFav");
		fav.setSelected(JavaBeamConfig.INSTANCE.getJavaBeamFav());
		fav.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent paramT) {
				JavaBeamConfig.INSTANCE.setJavaBeamFav(fav.isSelected());
			}
		});

		item.getItems().addAll(rt,fav);
	}
}
