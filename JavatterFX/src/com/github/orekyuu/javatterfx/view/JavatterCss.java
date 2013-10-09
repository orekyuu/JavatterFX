package com.github.orekyuu.javatterfx.view;

import java.io.File;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

public final class JavatterCss {

	private static final JavatterCss instance=new JavatterCss();

	private static File dir;

	private JavatterCss(){
		dir=new File("css");
		dir.mkdir();
	}

	public static JavatterCss getInstance(){
		return instance;
	}

	/**
	 * CSSのリストを返します
	 * @return
	 */
	public static String[] getCssPath(){
		List<String> list=new ArrayList<>();

		for(File file:dir.listFiles()){
			if(file.getName().endsWith(".css")){
				try {
					list.add(file.toURI().toURL().toExternalForm());
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
		}
		return list.toArray(new String[]{});
	}
}
