package orekyuu.plugin.loader;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public enum PluginRegister {

	INSTANCE;

	private Map<String,Object> plugins=new HashMap<String,Object>();

	/**
	 * �v���O�C����o�^����
	 * @param name
	 * @param obj
	 */
	protected void registerPlugin(String name,Object obj){
		System.out.println("plugin adding: "+name);
		plugins.put(name, obj);
	}

	/**
	 * �v���O�C���̔z���Ԃ�
	 * @return
	 */
	protected Object[] getPluginList(){
		List<Object> list=new ArrayList<Object>();
		for(Entry<String, Object> entry:plugins.entrySet()){
			list.add(entry.getValue());
		}
		return list.toArray(new Object[]{});
	}
}
