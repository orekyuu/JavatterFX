package com.github.orekyuu.javatterfx.managers;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;


/**
 * Column管理クラス
 * @author orekyuu
 *
 */
public enum ColumnManager {

	INSTANCE;

	private Map<String,ColumFactory> map=new HashMap<>();

	/**
	 * Columnを登録する
	 * @param factory
	 */
	public void put(ColumFactory factory){
		map.put(factory.getColumName(), factory);
	}

	/**
	 * Columnの名前の配列を返す
	 * @return
	 */
	public String[] columList(){
		String[] array=new String[map.size()];
		int i=0;
		for(Entry<String, ColumFactory> e:map.entrySet()){
			array[i++]=e.getKey();
		}
		return Arrays.copyOf(array, array.length);
	}

	/**
	 * ColumnFactoryを返す
	 * @param key
	 * @return
	 */
	public ColumFactory getColumFactory(String key){
		return map.get(key);
	}
}
