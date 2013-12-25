package orekyuu.plugin.loader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javafx.scene.control.MenuItem;

import com.github.orekyuu.javatterfx.event.system.EventPluginLoad;
import com.github.orekyuu.javatterfx.managers.EventManager;

public enum PluginRegister {

	INSTANCE;

	private Map<String,Object> plugins=new HashMap<>();
	private Map<String,MenuItem> confingMenu=new HashMap<>();

	/**
	 * プラグインを登録
	 * @param name
	 * @param version
	 * @param obj
	 */
	void registerPlugin(String name,String version, Object obj){
		System.out.println("plugin adding: "+name+" "+version);
		EventManager.INSTANCE.eventFire(new EventPluginLoad(name, version, obj));
		plugins.put(name, obj);
	}

	/**
	 * コンフィグ用のMenuItemを登録
	 * @param name プラグイン名
	 * @param item MenuItem
	 */
	void reginsterConfigMenu(String name,MenuItem item){
		confingMenu.put(name, item);
	}

	/**
	 * プラグインのリストを返す
	 * @return
	 */
	Object[] getPluginList(){
		List<Object> list=new ArrayList<>();
		for(Entry<String, Object> entry:plugins.entrySet()){
			list.add(entry.getValue());
		}
		return list.toArray(new Object[]{});
	}

	/**
	 * コンフィグ用のMenuItemの配列を返す
	 * @return
	 */
	public MenuItem[] getPluginConfigs(){
		List<MenuItem> list=new ArrayList<>();
		for(Entry<String, MenuItem> entry:confingMenu.entrySet()){
			list.add(entry.getValue());
		}
		return list.toArray(new MenuItem[]{});
	}

	/**
	 * ロード済みのプラグインの名前を返します
	 * @return
	 */
	public String[] getLoadedPluginNames(){
		String[] array=new String[plugins.size()];
		@SuppressWarnings("unchecked")
		Entry<String,Object>[] entrys=plugins.entrySet().toArray(new Entry[0]);
		for(int i=0;i<array.length;i++){
			array[i]=entrys[i].getKey();
		}
		return array;
	}
}
