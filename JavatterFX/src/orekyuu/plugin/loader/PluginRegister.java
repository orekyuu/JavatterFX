package orekyuu.plugin.loader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.github.orekyuu.javatterfx.event.system.EventPluginLoad;
import com.github.orekyuu.javatterfx.managers.EventManager;

public enum PluginRegister {

	INSTANCE;

	private Map<String,Object> plugins=new HashMap<String,Object>();

	/**
	 * プラグインを登録
	 * @param name
	 * @param version
	 * @param obj
	 */
	void registerPlugin(String name,String version, Object obj){
		System.out.println("plugin adding: "+name+" "+version);
		EventManager.INSTANCE.eventFire(new EventPluginLoad(name, version));
		plugins.put(name, obj);
	}

	/**
	 * プラグインのリストを返す
	 * @return
	 */
	Object[] getPluginList(){
		List<Object> list=new ArrayList<Object>();
		for(Entry<String, Object> entry:plugins.entrySet()){
			list.add(entry.getValue());
		}
		return list.toArray(new Object[]{});
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
