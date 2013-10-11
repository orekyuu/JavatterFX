package orekyuu.plugin.loader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import com.github.orekyuu.javatterfx.event.EventManager;
import com.github.orekyuu.javatterfx.event.system.EventPluginLoad;

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
}
