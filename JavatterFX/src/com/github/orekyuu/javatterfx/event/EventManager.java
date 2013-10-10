package com.github.orekyuu.javatterfx.event;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum EventManager {
	INSTANCE;

	private Map<Class<? extends Event>,List<EventValue>> map =
			new HashMap<Class<? extends Event>,List<EventValue>>();

	/**
	 * イベントリスナを登録する
	 * @param listener EventListener
	 */
	public void addEventListener(Listener listener){
		for(Method m:listener.getClass().getMethods()){
			if(isEventMethod(m)&&m.getParameterTypes().length==1
					&&isEvent(m.getParameterTypes()[0])){

				Class<? extends Event> key=(Class<? extends Event>) m.getParameterTypes()[0];

				//キーが無ければリストを新しく作っておく
				if(!map.containsKey(m.getParameterTypes()[0])){
					map.put(key, new ArrayList<EventValue>());
				}

				//リストにリスナを登録する
				map.get(key).add(new EventValue(m,listener));
			}
		}
	}

	/**
	 * イベントを発火させる
	 * @param event 発火させたいイベントの引数
	 */
	public void eventFire(Event event){
		if(!map.containsKey(event.getClass()))return;
		List<EventValue> list=map.get(event.getClass());
		for(EventValue v:list){
			try {
				v.getMethod().invoke(v.getInstance(), new Object[]{event});
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InvocationTargetException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * イベントかどうかを調べる
	 * @param target
	 * @return
	 */
	private boolean isEvent(Class target){
		Class parent=target.getSuperclass();
		while(parent==null||parent.equals(Event.class)){
			parent=parent.getSuperclass();
		}
		return parent!=null;
	}

	private boolean isEventMethod(Method method){
		for(Annotation a:method.getAnnotations()){
			if(a.annotationType().equals(EventHandler.class))
				return true;
		}
		return false;
	}

	private class EventValue{
		private Method method;
		private Listener instance;
		public EventValue(Method m, Listener listener) {
			method=m;
			instance=listener;
		}
		protected Method getMethod(){return method;}
		protected Listener getInstance(){return instance;}
	}
}