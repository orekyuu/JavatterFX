package orekyuu.javabeam;

import com.github.orekyuu.javatterfx.managers.SaveData;
import com.github.orekyuu.javatterfx.managers.SaveDataManager;

public enum JavaBeamConfig {

	INSTANCE;

	private SaveData data;
	void init(){
		data=SaveDataManager.getInstance().getSaveData("JavaBeamUtil");
	}

	boolean getJavaBeamRT(){
		return data.getBoolean("JavaBeamRT");
	}

	boolean getJavaBeamFav(){
		return data.getBoolean("JavaBeamFav");
	}

	void setJavaBeamRT(boolean flag){
		data.setBoolean("JavaBeamRT", flag);
	}

	void setJavaBeamFav(boolean flag){
		data.setBoolean("JavaBeamFav", flag);
	}
}
