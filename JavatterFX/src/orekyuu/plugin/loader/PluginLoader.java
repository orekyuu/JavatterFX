package orekyuu.plugin.loader;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class PluginLoader{

	public void load(){
		pluginPostInit();
	}

	/**
	 * PostInitを呼び出す
	 */
	private void pluginPostInit(){
		for(Object obj:PluginRegister.INSTANCE.getPluginList()){
			for(Method m:obj.getClass().getMethods()){
				if(equippedAnnotations(m.getAnnotations(), Plugin.PostInit.class)==null)
					continue;
				try {
					m.invoke(obj, null);
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				} catch (InvocationTargetException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * プラグインを追加する
	 * @param clazz
	 */
	private void addPlugin(Class clazz){
		Annotation plugin=getPluginAnnotation(clazz);//クラスに付けられているPluginアノテーションを取得する
		if(plugin==null)return ;//なければリターン
		Plugin p=(Plugin) plugin;

		try {
			Object obj=clazz.newInstance();//インスタンスを作成

			//PreInitを実行
			for(Method m:clazz.getMethods()){
				if(equippedAnnotations(m.getAnnotations(),
						orekyuu.plugin.loader.Plugin.PreInit.class)!=null){
						m.invoke(obj, (Object[])null);
				}
			}
			PluginRegister.INSTANCE.registerPlugin(p.name(), obj);//Pluginを登録する
			//Initを実行
			for(Method m:clazz.getMethods()){
				if(equippedAnnotations(m.getAnnotations(),
						orekyuu.plugin.loader.Plugin.Init.class)!=null){
						m.invoke(obj, (Object[])null);
				}
			}
		} catch (InstantiationException e1) {
			e1.printStackTrace();
		} catch (IllegalAccessException e1) {
			e1.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Pluginアノテーションを返す
	 * @param target 調べたい対象のクラス
	 * @return
	 */
	private Annotation getPluginAnnotation(Class target){
		Annotation[] list=equippedAnnotations(target.getAnnotations(), Plugin.class);
		if(list!=null){
			return list[0];
		}
		return null;
	}

	/**
	 * リストの中に指定されたアノテーションが存在すれば返す
	 * @param annotations アノテーションのリスト
	 * @param target 調べたいアノテーションクラス
	 * @return 見つかったアノテーションのリスト
	 */
	private Annotation[] equippedAnnotations(Annotation[] annotations,Class target){
		List<Annotation> list=new ArrayList<Annotation>();
		for(Annotation a:annotations){
			if(a.annotationType().equals(target)){
				list.add(a);
			}
		}
		if(list.size()==0)return null;
		return list.toArray(new Annotation[]{});
	}
}