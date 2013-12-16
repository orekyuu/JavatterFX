package orekyuu.plugin.loader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class PluginLoader{
	public void load(){
		File file=new File("plugins");
		if(!file.exists())file.mkdir();

		URLClassLoader loader=(URLClassLoader) getClass().getClassLoader();
		for(File f:file.listFiles()){
			if(f.getName().endsWith(".jar")){
				loadPlugin(f,loader);
			}
		}
		
		loadDevelopPlugin();
		pluginPostInit();
	}

	private void loadPlugin(File f,URLClassLoader loader) {
		try {
			addLibrary(f, loader);
			if(f.isFile()&&((f.getName().endsWith(".jar")) || (f.getName().endsWith(".zip")))){
				InputStream input = new FileInputStream(f);
				ZipInputStream zip = new ZipInputStream(input);
				ZipEntry entry = null;
				while (true) {
					entry = zip.getNextEntry();
					if (entry == null) {
						break;
					}
					String name = entry.getName();
					if ((!entry.isDirectory()) && (name.endsWith(".class")))
						loadPlugin(loader, name);
				}
				input.close();
			}
		} catch (NoSuchMethodException | SecurityException
				| IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | IOException e) {
			e.printStackTrace();
		}
	}

	private void loadPlugin(URLClassLoader loader, String name) {
		try {
			Class<?> clazz=loader.loadClass(name.replace(".class", "").replace('/', '.'));
			addPlugin(clazz);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}
	
	private void loadDevelopPlugin() {
		String path = System.getProperty("loadPlugin");
		if (path != null) {
			try {
				System.out.println(path);
				addPlugin(Class.forName(path));
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
	}

	public static void addLibrary(File file,ClassLoader loader) throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, MalformedURLException{
		Method m=URLClassLoader.class.getDeclaredMethod("addURL", new Class[]{URL.class});
		m.setAccessible(true);
		m.invoke(loader, new Object[]{file.toURI().toURL()});
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
					m.invoke(obj, (Object[])null);
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
	private void addPlugin(Class<?> clazz){
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
			PluginRegister.INSTANCE.registerPlugin(p.name(),p.version(), obj);//Pluginを登録する
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
	private Annotation getPluginAnnotation(Class<?> target){
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
	private Annotation[] equippedAnnotations(Annotation[] annotations,Class<?> target){
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